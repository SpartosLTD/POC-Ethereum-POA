package com.spartos.testclient.bets;

public class DeployContractsParams {

    private String nodeUrl;

    private String factoryContractAddress;

    private String spartosTokenContractAddress;

    private String desc;

    public String getNodeUrl() {
        return nodeUrl;
    }

    public String getFactoryContractAddress() {
        return factoryContractAddress;
    }

    public String getDesc() {
        return desc;
    }

    public String getSpartosTokenContractAddress() {
        return spartosTokenContractAddress;
    }
}
