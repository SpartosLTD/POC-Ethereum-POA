package com.spartos.testclient.bets;

public class DeployBettingContractResponse {

    private String bettingContractAddress;

    public DeployBettingContractResponse(String bettingContractAddress) {
        this.bettingContractAddress = bettingContractAddress;
    }

    public String getBettingContractAddress() {
        return bettingContractAddress;
    }

}
