package com.spartos.testclient.bets;

public class BettingContractMetricsResponse {

    private long betsCount;
    private long firstBetTimestamp;
    private long lastBetTimestamp;

    private long settlementsCount;
    private long firstSettlementTimestamp;
    private long lastSettlementTimestamp;

    private long placementDuration;
    private int averagePlacementTps;

    private long settlementDuration;
    private int averageSettlementTps;

    public BettingContractMetricsResponse() {
    }

    public BettingContractMetricsResponse(long betsCount, long firstBetTimestamp, long lastBetTimestamp, long settlementsCount, long firstSettlementTimestamp, long lastSettlementTimestamp) {
        this.betsCount = betsCount;
        this.firstBetTimestamp = firstBetTimestamp;
        this.lastBetTimestamp = lastBetTimestamp;
        placementDuration = lastBetTimestamp - firstBetTimestamp;
        if (placementDuration == 0) placementDuration = 1;
        averagePlacementTps = (int) (betsCount / placementDuration);

        this.settlementsCount = settlementsCount;
        this.firstSettlementTimestamp = firstSettlementTimestamp;
        this.lastSettlementTimestamp = lastSettlementTimestamp;

        settlementDuration = lastSettlementTimestamp - firstSettlementTimestamp;
        if (settlementDuration == 0) settlementDuration = 1;
        averageSettlementTps = (int) (settlementsCount / settlementDuration);
    }

    public long getBetsCount() {
        return betsCount;
    }

    public long getFirstBetTimestamp() {
        return firstBetTimestamp;
    }

    public long getLastBetTimestamp() {
        return lastBetTimestamp;
    }

    public long getPlacementDuration() {
        return placementDuration;
    }

    public int getAveragePlacementTps() {
        return averagePlacementTps;
    }

    public long getSettlementsCount() {
        return settlementsCount;
    }

    public long getFirstSettlementTimestamp() {
        return firstSettlementTimestamp;
    }

    public long getLastSettlementTimestamp() {
        return lastSettlementTimestamp;
    }

    public long getSettlementDuration() {
        return settlementDuration;
    }

    public int getAverageSettlementTps() {
        return averageSettlementTps;
    }
}
