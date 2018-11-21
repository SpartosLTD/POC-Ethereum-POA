package com.spartos.testclient.contracts.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class BettingOntology extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060098054600160a060020a031916331790819055600160a060020a031660009081526020819052604090206305f5e100905561047f806100526000396000f3006080604052600436106100985763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632164f938811461009d57806328cc1b78146100c45780632dead375146100e15780634745d3fb146100f6578063535de0471461010b57806378071d2b146101205780638c7e347a146101355780638f8f1b7c1461014a578063beabacc814610174575b600080fd5b3480156100a957600080fd5b506100b261019e565b60408051918252519081900360200190f35b3480156100d057600080fd5b506100df60ff600435166101a4565b005b3480156100ed57600080fd5b506100b261029e565b34801561010257600080fd5b506100b26102a4565b34801561011757600080fd5b506100b26102aa565b34801561012c57600080fd5b506100b26102b0565b34801561014157600080fd5b506100b26102b6565b34801561015657600080fd5b506100df600160a060020a036004351660243560ff604435166102bf565b34801561018057600080fd5b506100df600160a060020a03600435811690602435166044356103d1565b60035490565b60006101ae61042c565b60008092505b60075460ff8416101561027d576008600060078560ff168154811015156101d757fe5b60009182526020808320818304015460ff601f90931661010090810a90910483168552848201959095526040938401909220835160808101855281548084168252600160a060020a039690049590951692850183905260018101549385019390935260029092015482166060840181905292945092508516141561027257600954604083015161027291600160a060020a03169083906103d1565b6001909201916101b4565b600554151561028b57426005555b5050426006555050600480546001019055565b60025490565b60055490565b60045490565b60065490565b60015460ff1690565b6102c761042c565b50604080516080810182526001805460ff908116808452600160a060020a0380891660208087019182528688018a81528986166060890190815260009586526008835298852088518154945190951661010090810274ffffffffffffffffffffffffffffffffffffffff001996891660ff1996871681179790971617825591518189015598516002998a01805491881691851691909117905560078054808901825595529084047fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c688018054601f90951690910a9283029285021990931691909117909155825480831684019092169116179055905415156103c757426002555b5050426003555050565b600160a060020a03831660009081526020819052604081205490828210156103f857600080fd5b50600160a060020a039384166000908152602081905260408082209284900390925592909316825291902080549091019055565b604080516080810182526000808252602082018190529181018290526060810191909152905600a165627a7a72305820e5613adbf2c62ddba932c95b37c709fb579e98488867a94ed9d9cdeb39895d260029";

    public static final String FUNC_GETLASTBETTIMESTAMP = "getLastBetTimestamp";

    public static final String FUNC_SETTLE = "settle";

    public static final String FUNC_GETFIRSTBETTIMESTAMP = "getFirstBetTimestamp";

    public static final String FUNC_GETFIRSTSETTLEMENTTIMESTAMP = "getFirstSettlementTimestamp";

    public static final String FUNC_GETSETTLEMENTSCOUNT = "getSettlementsCount";

    public static final String FUNC_GETLASTSETTLEMENTTIMESTAMP = "getLastSettlementTimestamp";

    public static final String FUNC_GETBETSCOUNT = "getBetsCount";

    public static final String FUNC_PLACEBET = "placeBet";

    public static final String FUNC_TRANSFER = "transfer";

    @Deprecated
    protected BettingOntology(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BettingOntology(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BettingOntology(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BettingOntology(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> getLastBetTimestamp() {
        final Function function = new Function(FUNC_GETLASTBETTIMESTAMP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> settle(BigInteger outcome) {
        final Function function = new Function(
                FUNC_SETTLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(outcome)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getFirstBetTimestamp() {
        final Function function = new Function(FUNC_GETFIRSTBETTIMESTAMP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getFirstSettlementTimestamp() {
        final Function function = new Function(FUNC_GETFIRSTSETTLEMENTTIMESTAMP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getSettlementsCount() {
        final Function function = new Function(FUNC_GETSETTLEMENTSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getLastSettlementTimestamp() {
        final Function function = new Function(FUNC_GETLASTSETTLEMENTTIMESTAMP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getBetsCount() {
        final Function function = new Function(FUNC_GETBETSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> placeBet(String player, BigInteger amount, BigInteger outcome) {
        final Function function = new Function(
                FUNC_PLACEBET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(player), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint8(outcome)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transfer(String addressFrom, String addressTo, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addressFrom), 
                new org.web3j.abi.datatypes.Address(addressTo), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<BettingOntology> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BettingOntology.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<BettingOntology> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BettingOntology.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BettingOntology> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BettingOntology.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BettingOntology> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BettingOntology.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static BettingOntology load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BettingOntology(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BettingOntology load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BettingOntology(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BettingOntology load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BettingOntology(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BettingOntology load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BettingOntology(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
