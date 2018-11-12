package com.spartos.testclient.bets;

public class DeployFactoryAndSpartosContractResponse {

    private String factoryContractAddress;

    private String spartosTokenContractAddress;

    public DeployFactoryAndSpartosContractResponse(String factoryContractAddress, String spartosTokenContractAddress) {
        this.factoryContractAddress = factoryContractAddress;
        this.spartosTokenContractAddress = spartosTokenContractAddress;
    }

    public String getFactoryContractAddress() {
        return factoryContractAddress;
    }

    public String getSpartosTokenContractAddress() {
        return spartosTokenContractAddress;
    }
}
