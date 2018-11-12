package com.spartos.testclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class EthFunder {

    private static final Logger LOG = LoggerFactory.getLogger(EthFunder.class);

    private static final BigInteger GAS_PRICE = DefaultGasProvider.GAS_PRICE.multiply(BigInteger.TEN);
    private static final BigInteger GAS_LIMIT = DefaultGasProvider.GAS_LIMIT;

    public List<EthSendTransaction> fundAccounts(Credentials source, List<String> targets, BigInteger amountWei, Web3j node) {
        return fundAccounts(source, targets, amountWei, Collections.singletonList(node));
    }

    public List<EthSendTransaction> fundAccounts(Credentials source, List<String> targets, BigInteger amountWei, List<Web3j> nodes) {
        LOG.info("Funding account with ether for " + targets.size() + " addresses");


        List<CompletableFuture<EthSendTransaction>> sendEtherTransactions = new ArrayList<>();

        BigInteger nonce = getNonce(nodes.get(0), source.getAddress());

        for (int i = 0; i < targets.size(); i++) {
            try {
                Thread.sleep(0, 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int nodeIndex = i % nodes.size();
            Web3j node = nodes.get(nodeIndex);
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    nonce, GAS_PRICE, GAS_LIMIT, targets.get(i), amountWei);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, source);
            String hexValue = Numeric.toHexString(signedMessage);
            CompletableFuture<EthSendTransaction> receipt = node.ethSendRawTransaction(hexValue).sendAsync();
            sendEtherTransactions.add(receipt);
            nonce = nonce.add(BigInteger.ONE);
        }


        List<EthSendTransaction> result =
                sendEtherTransactions.stream()
                        .map(CompletableFuture::join)
                        .peek(ethSendTransaction -> {
                            if (ethSendTransaction.getError() != null) {
                                LOG.error(ethSendTransaction.getError().getMessage());
                            }
                        })
                        .collect(Collectors.toList());

        LOG.info(result.size() + " accounts have been funded with ether");
        return result;
    }

    public BigInteger getNonce(Web3j node, String address) {
        try {
            EthGetTransactionCount ethGetTransactionCount = node.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING)
                    .sendAsync().get();
            return ethGetTransactionCount.getTransactionCount();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return BigInteger.ZERO;
    }


}
