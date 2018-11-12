package com.spartos.testclient.bets;

public class SettlementParams {

    private String[] nodeUrls;
    private String bettingContractAddress;
    private int transactionsCount;

    public SettlementParams() {
    }

    public String[] getNodeUrls() {
        return nodeUrls;
    }

    public String getBettingContractAddress() {
        return bettingContractAddress;
    }

    public int getTransactionsCount() {
        return transactionsCount;
    }
}
