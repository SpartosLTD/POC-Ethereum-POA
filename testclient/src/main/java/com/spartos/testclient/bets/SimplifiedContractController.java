package com.spartos.testclient.bets;

import com.spartos.testclient.contracts.generated.BettingOntology;
import com.spartos.testclient.credentials.CredentialsGroup;
import com.spartos.testclient.credentials.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class SimplifiedContractController {

    private static final Logger LOG = LoggerFactory.getLogger(SimplifiedContractController.class);

    private CredentialsProvider credentialsProvider;

    private static final BigInteger GAS_PRICE = DefaultGasProvider.GAS_PRICE; //Set ZERO for quorum
    private static final BigInteger GAS_LIMIT = DefaultGasProvider.GAS_LIMIT;

    private static final ContractGasProvider CONTRACT_GAS_PROVIDER = new DefaultGasProvider();

    private static final boolean DO_DELAY = false;
    private static final long TX_DELAY_MS = 0;


    @Autowired
    public SimplifiedContractController(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    @PostMapping("/simple/deploy")
    public String deploySimplifiedContract(@RequestBody DeployContractsParams params) throws Exception {

        Credentials credentials = credentialsProvider.loadCredentials(CredentialsGroup.OWNER);
        Web3j node = Web3j.build(new HttpService(params.getNodeUrl()));

        BettingOntology contract = BettingOntology.deploy(
                node,
                credentials,
                CONTRACT_GAS_PROVIDER
                ).send();
        return contract.getContractAddress();

    }

    @PostMapping("/simple/bet")
    public BetPlacementResponse simpleBet(@RequestBody BetPlacementTestParams params) throws Exception {

        List<Web3j> nodeClients =
                Arrays.stream(params.getNodeUrls())
                        .map(node -> Web3j.build(new HttpService(node)))
                        .collect(Collectors.toList());

        Credentials ownerCredentials = credentialsProvider.loadCredentials(CredentialsGroup.OWNER);
        List<Credentials> playersCredentials = credentialsProvider.loadCredentials(CredentialsGroup.PLAYER, params.getPlayersCount());

        if (!params.isQuorum()) {
            try {
                fundAccounts(ownerCredentials,
                        playersCredentials,
                        Convert.toWei("100", Convert.Unit.ETHER).toBigInteger(),
                        nodeClients.get(0)
                );
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }

        BettingOntology bettingContract = BettingOntology.load(
                params.getBettingContractAddress(),
                nodeClients.get(0),
                ownerCredentials,
                CONTRACT_GAS_PROVIDER
        );

        pause();

        LOG.info("Starting to make bets via " + playersCredentials.size() + " players");

        List<CompletableFuture<EthSendTransaction>> betTransactionFutures = new ArrayList<>();

        playersCredentials.forEach(credentials -> {
            Web3j node = nodeClients.get(Math.abs(credentials.hashCode()) % nodeClients.size());
            int firstNonce = getNonce(node, credentials.getAddress()).intValue();
            for (int i = 0; i < params.getBetsPerPlayerCount(); i++) {

                if (DO_DELAY) {
                    try {
                        Thread.sleep(TX_DELAY_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                betTransactionFutures.add(
                        sendSimpleBetTransaction(
                                node,
                                credentials,
                                BigInteger.valueOf(firstNonce + i),
                                bettingContract,
                                BigInteger.valueOf(params.getBetAmount())
                        )
                );
            }
        });

        betTransactionFutures.stream()
                .map(CompletableFuture::join)
                .filter(ethSendTransaction -> ethSendTransaction.getError() != null)
                .forEach(ethSendTransaction -> LOG.info(ethSendTransaction.getError().getMessage()));

        return new BetPlacementResponse(params.getSpartosTokenContractAddress(),
                bettingContract.getContractAddress(),
                params.getPlayersCount() * params.getBetsPerPlayerCount(),
                0);

    }

    @PostMapping("/simple/settle")
    public SettlementResponse settle(@RequestBody SettlementParams params) throws Exception {

        List<Web3j> nodeClients =
                Arrays.stream(params.getNodeUrls())
                        .map(node -> Web3j.build(new HttpService(node)))
                        .collect(Collectors.toList());

        Credentials ownerCredentials = credentialsProvider.loadCredentials(CredentialsGroup.OWNER);

        BettingOntology bettingContract = BettingOntology.load(
                params.getBettingContractAddress(),
                nodeClients.get(0),
                ownerCredentials,
                CONTRACT_GAS_PROVIDER);

        Web3j node = nodeClients.get(0);
        BigInteger firstNonce = getNonce(node, ownerCredentials.getAddress());

        List<CompletableFuture<EthSendTransaction>> futures =
                IntStream.range(0, params.getTransactionsCount())
                        .mapToObj(i -> {
                            int nonce = firstNonce.intValue() + i;
                            Web3j randomNode = nodeClients.get(i % nodeClients.size());
                            return sendSimpleSettleTransaction(randomNode,
                                    ownerCredentials,
                                    BigInteger.valueOf(nonce),
                                    bettingContract,
                                    BigInteger.ONE
                            );
                        })
                        .collect(Collectors.toList());

        futures.stream()
                .map(CompletableFuture::join)
                .peek(ethSendTransaction -> {
                    if (ethSendTransaction.getError() != null) {
                        LOG.error(ethSendTransaction.getError().getMessage());
                    }
                })
                .collect(Collectors.toList());

        return new SettlementResponse(params.getBettingContractAddress(), params.getTransactionsCount());
    }


    private void pause() throws InterruptedException {
        Thread.sleep(5000);
    }

    private CompletableFuture<EthSendTransaction> sendSimpleBetTransaction(Web3j node, Credentials credentials, BigInteger nonce, BettingOntology bettingContract, BigInteger bet) {
        final Function function = new Function(
                BettingOntology.FUNC_PLACEBET,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(credentials.getAddress()),
                        new org.web3j.abi.datatypes.generated.Uint256(bet),
                        new org.web3j.abi.datatypes.generated.Uint8(BigInteger.ONE)),
                Collections.<TypeReference<?>>emptyList());

        return sendContractTransaction(node, function, credentials, nonce, bettingContract.getContractAddress());
    }

    private CompletableFuture<EthSendTransaction> sendSimpleSettleTransaction(Web3j node, Credentials credentials, BigInteger nonce, BettingOntology contract, BigInteger outcome) {
        final Function function = new Function(
                BettingOntology.FUNC_SETTLE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(credentials.getAddress()),
                        new org.web3j.abi.datatypes.generated.Uint8(outcome)),
                Collections.<TypeReference<?>>emptyList());

        return sendContractTransaction(node, function, credentials, nonce, contract.getContractAddress());
    }


    private CompletableFuture<EthSendTransaction> sendContractTransaction(Web3j node, Function function, Credentials from, BigInteger nonce, String contractAddress) {
        String encodedFunction = FunctionEncoder.encode(function);

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                GAS_PRICE,
                GAS_LIMIT,
                contractAddress,
                encodedFunction);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, from);
        String hexValue = Numeric.toHexString(signedMessage);
        return node.ethSendRawTransaction(hexValue).sendAsync();
    }


    private BigInteger getNonce(Web3j node, String address) {
        try {
            EthGetTransactionCount ethGetTransactionCount = node.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            return ethGetTransactionCount.getTransactionCount();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return BigInteger.ZERO;
    }

    private void fundAccounts(Credentials source, List<Credentials> targets, BigInteger amountWei, Web3j node) {
        LOG.info("Funding account with ether for " + targets.size() + " addresses");
        List<CompletableFuture<EthSendTransaction>> sendEtherTransactions = new ArrayList<>();
        BigInteger initialNonce = getNonce(node, source.getAddress());
        for (int i = 0; i < targets.size(); i++) {
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    BigInteger.valueOf(i).add(initialNonce), GAS_PRICE, GAS_LIMIT, targets.get(i).getAddress(), amountWei);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, source);
            String hexValue = Numeric.toHexString(signedMessage);
            CompletableFuture<EthSendTransaction> receipt = node.ethSendRawTransaction(hexValue).sendAsync();
            sendEtherTransactions.add(receipt);
        }

        sendEtherTransactions.stream()
                .map(CompletableFuture::join)
                .peek(ethSendTransaction -> {
                    if (ethSendTransaction.getError() != null) {
                        LOG.error(ethSendTransaction.getError().getMessage());
                    }
                })
                .collect(Collectors.toList());

        LOG.info(targets.size() + " accounts have been funded with ether");
    }

}
