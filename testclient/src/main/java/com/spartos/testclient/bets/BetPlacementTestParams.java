package com.spartos.testclient.bets;

public class BetPlacementTestParams {

    private String[] nodeUrls;
    private String spartosTokenContractAddress;
    private String bettingContractAddress;
    private int playersCount;
    private int betsPerPlayerCount;
    private int betAmount;
    private boolean quorum;

    public BetPlacementTestParams() {
    }

    public String getSpartosTokenContractAddress() {
        return spartosTokenContractAddress;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public int getBetsPerPlayerCount() {
        return betsPerPlayerCount;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public String[] getNodeUrls() {
        return nodeUrls;
    }

    public String getBettingContractAddress() {
        return bettingContractAddress;
    }

    public boolean isQuorum() {
        return quorum;
    }
}
