package com.spartos.testclient.bets;

public class SettlementResponse {

    private String bettingContractAddress;
    private int settlementsCount;

    public SettlementResponse(String bettingContractAddress, int settlementsCount) {
        this.bettingContractAddress = bettingContractAddress;
        this.settlementsCount = settlementsCount;
    }

    public String getBettingContractAddress() {
        return bettingContractAddress;
    }

    public int getSettlementsCount() {
        return settlementsCount;
    }
}
