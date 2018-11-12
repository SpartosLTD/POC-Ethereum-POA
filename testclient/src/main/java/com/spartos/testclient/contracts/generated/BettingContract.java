package com.spartos.testclient.contracts.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class BettingContract extends Contract {
    private static final String BINARY = "600e805460ff1916905560c0604052600360809081527f626574000000000000000000000000000000000000000000000000000000000060a0526200004d90640100000000620001b2810204565b600e80547c01000000000000000000000000000000000000000000000000000000009092046101000264ffffffff00199092169190911790553480156200009357600080fd5b5060405162001377380380620013778339810160409081528151602080840151928401516060850151608086015160a0870151949096018051909692949193620000e391600f9189019062000218565b5060008054600160a060020a0319908116600160a060020a039687161782556001805482169587169590951785556005805460ff191660ff9586161761ff0019166101009486168502179055601080549091169690951695909517909355600680548084018255948190527ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f602080870482018054601f988916880a8087021990911617905582549485019092559083040180549290941690920a9182021916600290910217905550620002bd565b6000816040518082805190602001908083835b60208310620001e65780518252601f199092019160209182019101620001c5565b5181516020939093036101000a6000190180199091169216919091179052604051920182900390912095945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200025b57805160ff19168380011785556200028b565b828001600101855582156200028b579182015b828111156200028b5782518255916020019190600101906200026e565b50620002999291506200029d565b5090565b620002ba91905b80821115620002995760008155600101620002a4565b90565b6110aa80620002cd6000396000f3006080604052600436106100cc5763ffffffff60e060020a6000350416631a09254181146100d15780632164f9381461015b57806328cc1b78146101825780632dead3751461019f57806337a02e62146101b45780634745d3fb146101de578063535de047146101f3578063599100eb1461020857806378071d2b1461022f5780638c7e347a146102445780639f4273f414610259578063c0ee0b8a14610274578063c6cbb8be146102dd578063cd61386d146102f2578063e3b8f53914610307578063f5621d1014610392575b600080fd5b3480156100dd57600080fd5b506100e66103ad565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610120578181015183820152602001610108565b50505050905090810190601f16801561014d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561016757600080fd5b50610170610443565b60408051918252519081900360200190f35b34801561018e57600080fd5b5061019d60ff60043516610449565b005b3480156101ab57600080fd5b506101706107ff565b3480156101c057600080fd5b5061019d600160a060020a036004351660243560ff60443516610805565b3480156101ea57600080fd5b50610170610b0c565b3480156101ff57600080fd5b50610170610b12565b34801561021457600080fd5b50610170600160a060020a036004351660ff60243516610b18565b34801561023b57600080fd5b50610170610b45565b34801561025057600080fd5b50610170610b4b565b34801561026557600080fd5b5061017060ff60043516610b51565b34801561028057600080fd5b50604080516020600460443581810135601f810184900484028501840190955284845261019d948235600160a060020a0316946024803595369594606494920191908190840183828082843750949750610b669650505050505050565b3480156102e957600080fd5b50610170610bdc565b3480156102fe57600080fd5b50610170610c68565b34801561031357600080fd5b506040805160206004803580820135601f8101849004840285018401909552848452610360943694929360249392840191908190840183828082843750949750610c6e9650505050505050565b604080517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff199092168252519081900360200190f35b34801561039e57600080fd5b5061019d60ff60043516610cd2565b600f8054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156104395780601f1061040e57610100808354040283529160200191610439565b820191906000526020600020905b81548152906001019060200180831161041c57829003601f168201915b5050505050905090565b600a5490565b600080548190819081908190600160a060020a031633146104b4576040805160e560020a62461bcd02815260206004820152601e60248201527f4e6f7420616c6c6f77656420746f20736574746c6520636f6e74726163740000604482015290519081900360640190fd5b8560ff16600114806104c957508560ff166002145b806104d757508560ff166003145b1515610552576040805160e560020a62461bcd028152602060048201526024808201527f436f6e7472616374206f6e6c7920737570706f72747320726573756c7473203160448201527f2c322c3300000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600c5415156105605742600c555b42600d55600b80546001019055600094505b60045460ff8616101561077c576004805460ff871690811061059057fe5b6000918252602082200154600160a060020a0316945092505b60065460ff84161015610771576006805460ff85169081106105c757fe5b90600052602060002090602091828204019190069054906101000a900460ff1660ff168660ff1614156106b457600554600160a060020a038516600090815260026020908152604080832060ff8b8116855292529091205461010083048216928216929092011602915060018211156106af576010546040805160008051602061105f8339815191528152600160a060020a038781166004830152600160248301529151919092169163a9059cbb91604480830192600092919082900301818387803b15801561069657600080fd5b505af11580156106aa573d6000803e3d6000fd5b505050505b610766565b8560ff16600314156107665750600160a060020a038316600090815260026020908152604080832060ff891684529091529020546001811115610766576010546040805160008051602061105f8339815191528152600160a060020a038781166004830152600160248301529151919092169163a9059cbb91604480830192600092919082900301818387803b15801561074d57600080fd5b505af1158015610761573d6000803e3d6000fd5b505050505b6001909201916105a9565b600190940193610572565b6001600781905560105481546040805160008051602061105f8339815191528152600160a060020a03928316600482015260248101949094525191169163a9059cbb91604480830192600092919082900301818387803b1580156107df57600080fd5b505af11580156107f3573d6000803e3d6000fd5b50505050505050505050565b60095490565b600e546000908190819060ff161561088d576040805160e560020a62461bcd02815260206004820152602960248201527f436f6e7472616374206973206c6f636b656420736f20646f65736e277420616360448201527f6365707420626574730000000000000000000000000000000000000000000000606482015290519081900360840190fd5b8360ff16600114806108a257508360ff166002145b15156108ad57600080fd5b6108b686610e1f565b600160a060020a038616600090815260026020908152604080832060ff881680855290835281842080548a81019091556003909352922080548801905593506001141561091157600554610100900460ff1685029150610928565b8360ff16600214156109285760055460ff16850291505b601054600154604080517fdd62ed3e000000000000000000000000000000000000000000000000000000008152600160a060020a0392831660048201523060248201529051919092169163dd62ed3e9160448083019260209291908290030181600087803b15801561099957600080fd5b505af11580156109ad573d6000803e3d6000fd5b505050506040513d60208110156109c357600080fd5b5051905081811015610a45576040805160e560020a62461bcd02815260206004820152603760248201527f43616e277420616363657074206265743a206e6f7420656e6f756768206f706560448201527f7261746f722066756e647320746f20636f766572206974000000000000000000606482015290519081900360840190fd5b601054600154604080517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a03928316600482015230602482015260448101869052905191909216916323b872dd9160648083019260209291908290030181600087803b158015610abd57600080fd5b505af1158015610ad1573d6000803e3d6000fd5b505050506040513d6020811015610ae757600080fd5b50506009541515610af757426009555b505042600a5550506008805460010190555050565b600c5490565b600b5490565b600160a060020a038216600090815260026020908152604080832060ff8516845290915290205492915050565b600d5490565b60085490565b60ff1660009081526003602052604090205490565b600080600080610b7585610f04565b600e54909450610100900460e060020a027bffffffffffffffffffffffffffffffffffffffffffffffffffffffff199081169085161415610bd35760049250610bbe8584610fec565b91505060e060020a8104610bd3878783610805565b50505050505050565b600e5460009060ff161515610c61576040805160e560020a62461bcd02815260206004820152602160248201527f436f6e747261637420686173206e6f74206265656e20736574746c656420796560448201527f7400000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b5060075490565b60045490565b6000816040518082805190602001908083835b60208310610ca05780518252601f199092019160209182019101610c81565b5181516020939093036101000a6000190180199091169216919091179052604051920182900390912095945050505050565b6000805b600454821015610dff576004805483908110610cee57fe5b60009182526020822001546010546040805160008051602061105f8339815191528152600160a060020a0393841660048201819052600160248301529151919550919092169263a9059cbb9260448084019382900301818387803b158015610d5557600080fd5b505af1158015610d69573d6000803e3d6000fd5b50506010546040805160008051602061105f83398151915281527365eba5f67dab356f9c21a25b16d30c0ab503a1cf6004820152600160248201529051600160a060020a03909216935063a9059cbb925060448082019260009290919082900301818387803b158015610ddb57600080fd5b505af1158015610def573d6000803e3d6000fd5b505060019093019250610cd69050565b600c541515610e0d5742600c555b505042600d5550600b80546001019055565b6000805b60065460ff82161015610e9f57600160a060020a03831660009081526002602052604081206006805483919060ff8616908110610e5c57fe5b600091825260208083208183040154601f9092166101000a90910460ff1683528201929092526040019020541115610e975760019150610e9f565b600101610e23565b811515610eff57600480546001810182556000919091527f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b01805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0385161790555b505050565b60008060006018846000815181101515610f1a57fe5b90602001015160f860020a900460f860020a0260f860020a900463ffffffff169060020a026010856001815181101515610f5057fe5b90602001015160f860020a900460f860020a0260f860020a900463ffffffff169060020a026008866002815181101515610f8657fe5b90602001015160f860020a900460f860020a0260f860020a900463ffffffff169060020a02866003815181101515610fba57fe5b90602001015160f860020a900460f860020a0260f860020a900401010191508160e060020a0290508092505050919050565b600080805b600481101561105657806008028582860181518110151561100e57fe5b60209101015160029190910a60f860020a918290049091027fff0000000000000000000000000000000000000000000000000000000000000016049190911790600101610ff1565b5093925050505600a9059cbb00000000000000000000000000000000000000000000000000000000a165627a7a72305820b174dca6e8069584334b974a81be8f96b920d59240de9f1ebccf69e22595e2df0029";

    public static final String FUNC_GETDESCRIPTION = "getDescription";

    public static final String FUNC_GETLASTBETTIMESTAMP = "getLastBetTimestamp";

    public static final String FUNC_SETTLE = "settle";

    public static final String FUNC_GETFIRSTBETTIMESTAMP = "getFirstBetTimestamp";

    public static final String FUNC_BET = "bet";

    public static final String FUNC_GETFIRSTSETTLEMENTTIMESTAMP = "getFirstSettlementTimestamp";

    public static final String FUNC_GETSETTLEMENTSCOUNT = "getSettlementsCount";

    public static final String FUNC_EXISTINGBET = "existingBet";

    public static final String FUNC_GETLASTSETTLEMENTTIMESTAMP = "getLastSettlementTimestamp";

    public static final String FUNC_GETBETSCOUNT = "getBetsCount";

    public static final String FUNC_GETTOTALBETS = "getTotalBets";

    public static final String FUNC_TOKENFALLBACK = "tokenFallback";

    public static final String FUNC_GETTRANSFERREDTOOPEATORAMOUNTAFTERSETTLEMENT = "getTransferredToOpeatorAmountAfterSettlement";

    public static final String FUNC_GETPLAYERSLENGTH = "getPlayersLength";

    public static final String FUNC_WHATTHEHASH = "whatTheHash";

    public static final String FUNC_SETTLELIGHT = "settleLight";

    public static final Event BETACCEPTED_EVENT = new Event("BetAccepted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
    ;

    protected BettingContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BettingContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> getDescription() {
        final Function function = new Function(FUNC_GETDESCRIPTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getLastBetTimestamp() {
        final Function function = new Function(FUNC_GETLASTBETTIMESTAMP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> settle(BigInteger side) {
        final Function function = new Function(
                FUNC_SETTLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(side)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getFirstBetTimestamp() {
        final Function function = new Function(FUNC_GETFIRSTBETTIMESTAMP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> bet(String player, BigInteger amount, BigInteger side) {
        final Function function = new Function(
                FUNC_BET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(player), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint8(side)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteCall<BigInteger> existingBet(String _player, BigInteger _side) {
        final Function function = new Function(FUNC_EXISTINGBET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_player), 
                new org.web3j.abi.datatypes.generated.Uint8(_side)), 
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

    public RemoteCall<BigInteger> getTotalBets(BigInteger side) {
        final Function function = new Function(FUNC_GETTOTALBETS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(side)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> tokenFallback(String _from, BigInteger _value, byte[] _data) {
        final Function function = new Function(
                FUNC_TOKENFALLBACK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getTransferredToOpeatorAmountAfterSettlement() {
        final Function function = new Function(FUNC_GETTRANSFERREDTOOPEATORAMOUNTAFTERSETTLEMENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getPlayersLength() {
        final Function function = new Function(FUNC_GETPLAYERSLENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> whatTheHash(String _src) {
        final Function function = new Function(FUNC_WHATTHEHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_src)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> settleLight(BigInteger side) {
        final Function function = new Function(
                FUNC_SETTLELIGHT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(side)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<BettingContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _desc, String _spartosTokenContract, String _allowedSettler, String _operator, BigInteger _odds_side_1, BigInteger _odds_side_2) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_desc), 
                new org.web3j.abi.datatypes.Address(_spartosTokenContract), 
                new org.web3j.abi.datatypes.Address(_allowedSettler), 
                new org.web3j.abi.datatypes.Address(_operator), 
                new org.web3j.abi.datatypes.generated.Uint8(_odds_side_1), 
                new org.web3j.abi.datatypes.generated.Uint8(_odds_side_2)));
        return deployRemoteCall(BettingContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<BettingContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _desc, String _spartosTokenContract, String _allowedSettler, String _operator, BigInteger _odds_side_1, BigInteger _odds_side_2) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_desc), 
                new org.web3j.abi.datatypes.Address(_spartosTokenContract), 
                new org.web3j.abi.datatypes.Address(_allowedSettler), 
                new org.web3j.abi.datatypes.Address(_operator), 
                new org.web3j.abi.datatypes.generated.Uint8(_odds_side_1), 
                new org.web3j.abi.datatypes.generated.Uint8(_odds_side_2)));
        return deployRemoteCall(BettingContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<BetAcceptedEventResponse> getBetAcceptedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BETACCEPTED_EVENT, transactionReceipt);
        ArrayList<BetAcceptedEventResponse> responses = new ArrayList<BetAcceptedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BetAcceptedEventResponse typedResponse = new BetAcceptedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._player = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._side = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BetAcceptedEventResponse> betAcceptedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BetAcceptedEventResponse>() {
            @Override
            public BetAcceptedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BETACCEPTED_EVENT, log);
                BetAcceptedEventResponse typedResponse = new BetAcceptedEventResponse();
                typedResponse.log = log;
                typedResponse._player = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._side = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<BetAcceptedEventResponse> betAcceptedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BETACCEPTED_EVENT));
        return betAcceptedEventObservable(filter);
    }

    public static BettingContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BettingContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static BettingContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BettingContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class BetAcceptedEventResponse {
        public Log log;

        public String _player;

        public BigInteger _amount;

        public BigInteger _side;
    }
}
