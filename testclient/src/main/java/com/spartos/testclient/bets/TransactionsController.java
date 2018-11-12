package com.spartos.testclient.bets;

import com.spartos.testclient.contracts.generated.BettingContract;
import com.spartos.testclient.contracts.generated.BettingContractFactory;
import com.spartos.testclient.contracts.generated.SpartosToken;
import com.spartos.testclient.credentials.CredentialsGroup;
import com.spartos.testclient.credentials.CredentialsProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import rx.Observer;
import rx.Subscriber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.spartos.testclient.contracts.generated.BettingContract.FUNC_BET;
import static com.spartos.testclient.contracts.generated.BettingContract.FUNC_SETTLE;
import static com.spartos.testclient.contracts.generated.SpartosToken.FUNC_TRANSFER;

@RestController
public class TransactionsController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsController.class);

    private CredentialsProvider credentialsProvider;

    private static final BigInteger ODDS_SIDE_1 = BigInteger.valueOf(1);
    private static final BigInteger ODDS_SIDE_2 = BigInteger.valueOf(2);

    private static final byte[] DATA = Hex.decode("95568a4b00000001"); //'bet' function signature and side to bet on (1)

    private static final BigInteger GAS_PRICE = DefaultGasProvider.GAS_PRICE; //Set ZERO for quorum
    private static final BigInteger GAS_LIMIT = DefaultGasProvider.GAS_LIMIT;

    private static final boolean DO_DELAY = false;
    private static final long TX_DELAY_MS = 2;


    @Autowired
    public TransactionsController(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    @GetMapping("/metrics/{bettingContractAddress}")
    public BettingContractMetricsResponse getMetrics(@PathVariable("bettingContractAddress") String bettingContractAddress, @RequestParam("nodeUrl") String nodeUrl) throws Exception {
        Credentials anyCredentials = credentialsProvider.loadCredentials(CredentialsGroup.PLAYER);
        Web3j node = Web3j.build(new HttpService(nodeUrl));
        BettingContract bettingContract = BettingContract.load(
                bettingContractAddress,
                node,
                anyCredentials,
                GAS_PRICE,
                GAS_LIMIT
        );



        BigInteger settlementsCount = null;
        try {
            settlementsCount = bettingContract.getSettlementsCount().send();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        if (settlementsCount != null && settlementsCount.intValue() > 0) {

            return new BettingContractMetricsResponse(
                    bettingContract.getBetsCount().send().longValue(),
                    bettingContract.getFirstBetTimestamp().send().longValue(),
                    bettingContract.getLastBetTimestamp().send().longValue(),
                    bettingContract.getSettlementsCount().send().longValue(),
                    bettingContract.getFirstSettlementTimestamp().send().longValue(),
                    bettingContract.getLastSettlementTimestamp().send().longValue()
            );
        } else {

            return new BettingContractMetricsResponse(
                    bettingContract.getBetsCount().send().longValue(),
                    bettingContract.getFirstBetTimestamp().send().longValue(),
                    bettingContract.getLastBetTimestamp().send().longValue(),
                    0,
                    0,
                    0
            );
        }

    }

    @PostMapping("/deployFactoryAndSpartosContract")
    public DeployFactoryAndSpartosContractResponse deployFactoryAndSpartosContract(@RequestBody DeployContractsParams params) throws Exception {
        Credentials credentials = credentialsProvider.loadCredentials(CredentialsGroup.OWNER);
        Web3j node = Web3j.build(new HttpService(params.getNodeUrl()));

        SpartosToken spartosToken = deploySpartosTokenContract(credentials, node);

        BettingContractFactory contract = BettingContractFactory.deploy(
                node,
                credentials,
                GAS_PRICE,
                GAS_LIMIT).send();

        return new DeployFactoryAndSpartosContractResponse(contract.getContractAddress(), spartosToken.getContractAddress());
    }

    @PostMapping("/deployBettingContract")
    public DeployBettingContractResponse deployBettingContract(@RequestBody DeployContractsParams params) throws Exception {
        Credentials credentials = credentialsProvider.loadCredentials(CredentialsGroup.OWNER);

        Web3j node = Web3j.build(new HttpService(params.getNodeUrl()));

        BettingContractFactory factoryContract = BettingContractFactory.load(params.getFactoryContractAddress(),
                node,
                credentials,
                GAS_PRICE,
                GAS_LIMIT);

        factoryContract.create(params.getDesc(), params.getSpartosTokenContractAddress(), credentials.getAddress(),
                credentials.getAddress(), ODDS_SIDE_1, ODDS_SIDE_2).sendAsync();

        //FIXME .first works fine here unless we have multiple contracts submitted concurrently. Need to add additional checks (introduce
        // some kind of contractId and also make sure contract address received in event owned by us to prevent someone create contract
        // with similar ID getting it from pending transactions pool.
        BettingContractFactory.BettingContractCreatedEventResponse event =
                factoryContract
                        .bettingContractCreatedEventObservable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST)
                        .toBlocking()
                        .first();

        return new DeployBettingContractResponse(event.contractAddress);


    }

    private Observer<Transaction> txObserver = new Subscriber<Transaction>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            LOG.error("TX error: " + e.getMessage());
        }

        @Override
        public void onNext(Transaction tx) {
            //LOG.info("Gas spent: " + tx.g() );
        }
    };

    private Observer<EthBlock> blockSubscriber = new Subscriber<EthBlock>() {
        @Override
        public void onCompleted() {}

        @Override
        public void onError(Throwable e) {
            LOG.error("Block error: " + e.getMessage());
        }

        @Override
        public void onNext(EthBlock ethBlock) {

            LOG.info("New block by " + ethBlock.getBlock().getMiner() + "(" + ethBlock.getBlock().getNumber() + "): " + ethBlock.getBlock().getTransactions().size() + " txs");
        }
    };

    private boolean subscribed;

    private void subscribeForBlocks(Web3j node) {
        if (!subscribed) {
            node.transactionObservable().subscribe(txObserver);
            node.blockObservable(false)
                    .filter(ethBlock -> ethBlock.getBlock().getTransactions().size() > 0)
                    .subscribe(blockSubscriber);
            subscribed = true;
        }
    }

    @PostMapping("/settle")
    public SettlementResponse settle(@RequestBody SettlementParams params) throws Exception {

        List<Web3j> nodeClients =
                Arrays.stream(params.getNodeUrls())
                        .map(node -> Web3j.build(new HttpService(node)))
                        .collect(Collectors.toList());

        Credentials ownerCredentials = credentialsProvider.loadCredentials(CredentialsGroup.OWNER);

        BettingContract bettingContract = BettingContract.load(
                params.getBettingContractAddress(),
                nodeClients.get(0),
                ownerCredentials,
                GAS_PRICE,
                GAS_LIMIT);

        subscribeForBlocks(nodeClients.get(0));

        LOG.info("Players to settle: " + bettingContract.getPlayersLength().send());

        Web3j node = nodeClients.get(0);
        BigInteger firstNonce = getNonce(node, ownerCredentials.getAddress());


        List<CompletableFuture<EthSendTransaction>> futures =
                IntStream.range(0, params.getTransactionsCount())
                        .mapToObj(i -> {
                            int nonce = firstNonce.intValue() + i;
                            return sendSettleTransaction(node,
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

        //Waiting for settlement to complete
        int settlementsCount = 0;
        boolean allSettled = false;
        try {
            while (!allSettled) {
                pause(); pause();
                int updatedSettlementsCount = bettingContract.getSettlementsCount().send().intValue();
                if (updatedSettlementsCount == settlementsCount) {
                    allSettled = true;
                }
                settlementsCount = updatedSettlementsCount;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return new SettlementResponse(params.getBettingContractAddress(), settlementsCount);
    }


    @PostMapping("/placeBets")
    public BetPlacementResponse placeBets(@RequestBody BetPlacementTestParams params) throws Exception {

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

        fundAdressesWithSpartos(params.getSpartosTokenContractAddress(),
                ownerCredentials,
                playersCredentials,
                BigInteger.valueOf(params.getBetAmount()),
                nodeClients.get(0));


        BigInteger operatorCoverage = BigInteger.valueOf(playersCredentials.size() * params.getBetAmount() *  params.getBetsPerPlayerCount() * (ODDS_SIDE_1.intValue() + ODDS_SIDE_2.intValue()));
        LOG.info("Operator requires " + operatorCoverage.intValue() + " coverage amount");


        //Allow betting contract cover bets with operator funds
        SpartosToken spartosToken = SpartosToken.load(
                params.getSpartosTokenContractAddress(),
                nodeClients.get(0),
                ownerCredentials,
                GAS_PRICE,
                GAS_LIMIT);


        BettingContract bettingContract = BettingContract.load(
                params.getBettingContractAddress(),
                nodeClients.get(0),
                ownerCredentials,
                GAS_PRICE,
                GAS_LIMIT
        );

        pause();

        try {
            spartosToken.approve(params.getBettingContractAddress(), operatorCoverage).send();
        } catch (Exception ignored) {}

        pause();

        LOG.info("Starting to make bets via " + playersCredentials.size() + " players");

        subscribeForBlocks(nodeClients.get(0));

        List<CompletableFuture<EthSendTransaction>> betTransactionFutures = new ArrayList<>();

        long begin = System.currentTimeMillis();
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
                        sendBetTransaction(
                                node,
                                credentials,
                                BigInteger.valueOf(firstNonce + i),
                                params.getSpartosTokenContractAddress(),
                                bettingContract,
                                DATA,
                                BigInteger.valueOf(params.getBetAmount())
                        )
                );
            }
        });

        betTransactionFutures.stream()
                .map(CompletableFuture::join)
                .filter(ethSendTransaction -> ethSendTransaction.getError() != null)
                .forEach(ethSendTransaction -> LOG.info(ethSendTransaction.getError().getMessage()));

        long elapsed = (System.currentTimeMillis() - begin) / 1000;

        //Waiting for placement to complete
        int txCount = 0;
        boolean allPlaced = false;
        try {
            while (!allPlaced) {
                pause();
                int updatedPlacementCount = bettingContract.getBetsCount().send().intValue();
                if (txCount == updatedPlacementCount) {
                    allPlaced = true;
                }
                txCount = updatedPlacementCount;
                LOG.info(txCount + " bets placed");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }


        return new BetPlacementResponse(params.getSpartosTokenContractAddress(),
                bettingContract.getContractAddress(),
                txCount,
                elapsed);

    }

    private void pause() throws InterruptedException {
        Thread.sleep(5000);
    }

    private CompletableFuture<EthSendTransaction> sendSettleTransaction(Web3j node, Credentials credentials, BigInteger nonce, BettingContract bettingContract, BigInteger side) {

        final Function function = new Function(
                FUNC_SETTLE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(side)),
                Collections.<TypeReference<?>>emptyList());

        return sendContractTransaction(node, function, credentials, nonce, bettingContract.getContractAddress());
    }


    private CompletableFuture<EthSendTransaction> sendBetTransaction(Web3j node, Credentials credentials, BigInteger nonce, String spartosTokenContractAddress, BettingContract bettingContract, byte[] DATA, BigInteger bet) {

        //Directly place bet, players need no money
        final Function function = new Function(
                FUNC_BET,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(credentials.getAddress()),
                        new org.web3j.abi.datatypes.generated.Uint256(bet),
                        new org.web3j.abi.datatypes.generated.Uint8(BigInteger.ONE)),
                Collections.<TypeReference<?>>emptyList());

        return sendContractTransaction(node, function, credentials, nonce, bettingContract.getContractAddress());

        /*final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(bettingContract.getContractAddress()),
                        new org.web3j.abi.datatypes.generated.Uint256(bet),
                        new org.web3j.abi.datatypes.DynamicBytes(DATA)),
                Collections.<TypeReference<?>>emptyList());

        return sendContractTransaction(node, function, credentials, nonce, spartosTokenContractAddress);*/
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


    private SpartosToken deploySpartosTokenContract(Credentials contractOwner, Web3j node) throws Exception {
        LOG.info("Deploying SpartosToken contract");

        SpartosToken spartosToken = SpartosToken.deploy(
                node,
                contractOwner,
                GAS_PRICE,
                GAS_LIMIT).send();

        return spartosToken;
    }

    private BettingContract deployBettingContract(String spartosTokenContractAddress,
                                                  Credentials operatorCredentials,
                                                  Web3j node) throws Exception {
        LOG.info("Deploying BettingContract");

        Credentials ownerCredentials = credentialsProvider.loadCredentials(CredentialsGroup.OWNER);

        BettingContract bettingContract = BettingContract.deploy(
                node,
                ownerCredentials,
                GAS_PRICE,
                GAS_LIMIT,
                "Spring-Boot",
                spartosTokenContractAddress,
                ownerCredentials.getAddress(),
                operatorCredentials.getAddress(),
                ODDS_SIDE_1,
                ODDS_SIDE_2)
                .send();

        String bettingContractAddress = bettingContract.getContractAddress();
        LOG.info("Betting contract has been deployed to " + bettingContractAddress);

        LOG.info("Testing description field of BettingContract: " +
                bettingContract.getDescription().send());

        return bettingContract;
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


    private void fundAdressesWithSpartos(String spartosContractAddress, Credentials from, List<Credentials> targets, BigInteger amount, Web3j node) {
        List<String> addresses = targets.stream().map(Credentials::getAddress).collect(Collectors.toList());
        fundAccountsWithSpartos(spartosContractAddress, from, addresses, amount, node);
    }

    private void fundAccountsWithSpartos(String spartosContractAddress, Credentials from, List<String> targets, BigInteger amount, Web3j node) {
        LOG.info("Funding account with Spartos for " + targets.size() + " addresses");
        List<CompletableFuture<EthSendTransaction>> sendEtherTransactions = new ArrayList<>();
        int initialNonce = getNonce(node, from.getAddress()).intValue();

        List<CompletableFuture<EthSendTransaction>> futures =
                IntStream.range(0, targets.size())
                        .mapToObj(i -> {
                            final Function function = new Function(
                                    FUNC_TRANSFER,
                                    Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(targets.get(i)),
                                            new org.web3j.abi.datatypes.generated.Uint256(amount)),
                                    Collections.<TypeReference<?>>emptyList());

                            return sendContractTransaction(node, function, from,
                                    BigInteger.valueOf(initialNonce + i), spartosContractAddress);
                        })
                        .collect(Collectors.toList());

        List<EthSendTransaction> txs = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        LOG.info(txs.size() + " accounts have been funded with Spartos");
    }


}
