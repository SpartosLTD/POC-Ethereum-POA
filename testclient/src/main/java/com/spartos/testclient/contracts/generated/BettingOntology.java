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
    private static final String BINARY = "608060405234801561001057600080fd5b5060098054600160a060020a031916331790819055600160a060020a03166000908152602081905260409020633b9aca009055610438806100526000396000f3006080604052600436106100985763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632164f938811461009d57806328cc1b78146100c45780632dead375146100e15780634745d3fb146100f6578063535de0471461010b57806378071d2b146101205780638c7e347a146101355780638f8f1b7c1461014a578063beabacc814610174575b600080fd5b3480156100a957600080fd5b506100b261019e565b60408051918252519081900360200190f35b3480156100d057600080fd5b506100df60ff600435166101a4565b005b3480156100ed57600080fd5b506100b2610283565b34801561010257600080fd5b506100b2610289565b34801561011757600080fd5b506100b261028f565b34801561012c57600080fd5b506100b2610295565b34801561014157600080fd5b506100b261029b565b34801561015657600080fd5b506100df600160a060020a036004351660243560ff604435166102a1565b34801561018057600080fd5b506100df600160a060020a036004358116906024351660443561038a565b60035490565b60006101ae6103e5565b60008092505b60075483101561026257600860006007858154811015156101d157fe5b6000918252602080832090910154835282810193909352604091820190208151608081018352815481526001820154600160a060020a03169381018490526002820154928101929092526003015460ff90811660608301819052919450919250908516141561025757600954604083015161025791600160a060020a031690839061038a565b6001909201916101b4565b600554151561027057426005555b5050426006555050600480546001019055565b60025490565b60055490565b60045490565b60065490565b60015490565b6102a96103e5565b506040805160808101825260018054808352600160a060020a03878116602080860191825285870189815260ff898116606089019081526000968752600890935297852087518082559351818801805473ffffffffffffffffffffffffffffffffffffffff191691909616179094555160028085019190915590516003909301805460ff1916939097169290921790955560078054808501825592527fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c688909101939093558054810190559054151561038057426002555b5050426003555050565b600160a060020a03831660009081526020819052604081205490828210156103b157600080fd5b50600160a060020a039384166000908152602081905260408082209284900390925592909316825291902080549091019055565b604080516080810182526000808252602082018190529181018290526060810191909152905600a165627a7a72305820ded4290f8527a880a60f5538b64c0eb81b78e44959d7283aa070df3007c19e290029";

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
