package com.spartos.testclient.bets;

public class BetPlacementResponse {

    private String spartosTokenContractAddress;
    private String bettingContractAddress;
    private int expectedBetsCount;
    private long elapsedTimeSeconds;

    public BetPlacementResponse(String spartosTokenContractAddress, String bettingContractAddress, int expectedBetsCount, long elapsedTimeSeconds) {
        this.bettingContractAddress = bettingContractAddress;
        this.spartosTokenContractAddress = spartosTokenContractAddress;
        this.expectedBetsCount = expectedBetsCount;
        this.elapsedTimeSeconds = elapsedTimeSeconds;
    }

    public String getSpartosTokenContractAddress() {
        return spartosTokenContractAddress;
    }

    public String getBettingContractAddress() {
        return bettingContractAddress;
    }

    public int getExpectedBetsCount() {
        return expectedBetsCount;
    }

    public long getElapsedTimeSeconds() {
        return elapsedTimeSeconds;
    }
}
