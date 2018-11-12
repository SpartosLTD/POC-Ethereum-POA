package com.spartos.testclient.transfers;

import com.spartos.testclient.EthFunder;
import com.spartos.testclient.credentials.CredentialsGroup;
import com.spartos.testclient.credentials.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class TransfersController {

    private static final Logger LOG = LoggerFactory.getLogger(TransfersController.class);

    private CredentialsProvider credentialsProvider;
    private EthFunder ethFunder;

    @Autowired
    public TransfersController(CredentialsProvider credentialsProvider, EthFunder ethFunder) {
        this.credentialsProvider = credentialsProvider;
        this.ethFunder = ethFunder;
    }

    @PostMapping("/testTransfers")
    public TransfersTestResponse testTransfers(@RequestBody TransfersTestParams params) throws IOException, InterruptedException {

        List<Web3j> nodeClients =
                Arrays.stream(params.getNodeUrls())
                        .map(node -> Web3j.build(new HttpService(node)))
                        .collect(Collectors.toList());


        Web3j node = nodeClients.get(0);

        Credentials ownerCredentials = credentialsProvider.loadCredentials(CredentialsGroup.OPERATOR);
        List<Credentials> playersCredentials = credentialsProvider.loadCredentials(CredentialsGroup.PLAYER, params.getCount());

        long begin = System.currentTimeMillis();

        BigInteger beginBalance = getEthBalance(node, ownerCredentials.getAddress());
        LOG.info("Starting balance: " + beginBalance);


        List<Web3j> nodes = new ArrayList<>();
        nodes.add(nodeClients.get(0));
        if (nodeClients.size() > 1) nodes.add(nodeClients.get(1));

        List<EthSendTransaction> transactions = ethFunder.fundAccounts(ownerCredentials,
                playersCredentials.stream().map(Credentials::getAddress).collect(Collectors.toList()),
                Convert.toWei("1", Convert.Unit.ETHER).toBigInteger(),
                nodes
        );


        BigInteger endingBalance = getEthBalance(node, ownerCredentials.getAddress());
        LOG.info("Ending balance: " + endingBalance);


        BigInteger ethTransferred = beginBalance.subtract(endingBalance);

        while (ethTransferred.intValue() < params.getCount()) {
            endingBalance = getEthBalance(node, ownerCredentials.getAddress());
            ethTransferred = beginBalance.subtract(endingBalance);
            LOG.info("Eth transferred: " + ethTransferred);
            Thread.sleep(1000);
        }

        long elapsed = System.currentTimeMillis() - begin;
        LOG.info("Ether transferred: " + ethTransferred);

        LOG.info(transactions.size() + " Eth transfers have been completed in " + (elapsed / 1000) + "s");

        return new TransfersTestResponse(ethTransferred.intValue(), (int) (elapsed / 1000));
    }

    private BigInteger getEthBalance(Web3j node, String address) throws IOException {
        return Convert.fromWei(node.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance().toString(), Convert.Unit.ETHER).toBigInteger();
    }

    @GetMapping("/metrics/transfers/{address}")
    public void testTransfers(@PathVariable("address") String address, @RequestParam("nodeUrl") String nodeUrl) {

    }

    @PostMapping("/giveMeEther")
    public void giveMeEther(@RequestBody FundEtherRequest request) {

        Web3j node = Web3j.build(new HttpService(request.getNodeUrl()));

        int amount = 1; //default amount
        if (request.getAmount() > 0.01 && request.getAmount() <= 10) {
            amount = request.getAmount();
        }

        ethFunder.fundAccounts(credentialsProvider.loadCredentials(CredentialsGroup.OWNER),
                Collections.singletonList(request.getAddress()),
                BigInteger.valueOf(amount),
                node);
    }

}
