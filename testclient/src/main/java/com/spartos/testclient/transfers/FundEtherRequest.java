package com.spartos.testclient.transfers;

public class FundEtherRequest {

    private String nodeUrl;

    private String address;

    private int amount;

    public String getNodeUrl() {
        return nodeUrl;
    }

    public String getAddress() {
        return address;
    }

    public int getAmount() {
        return amount;
    }
}
