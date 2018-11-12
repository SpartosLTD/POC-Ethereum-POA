package com.spartos.testclient.transfers;

public class TransfersTestResponse {

    private int count;

    private int seconds;

    private int transferTps;

    public TransfersTestResponse(int count, int seconds) {
        this.count = count;
        this.seconds = seconds;
        transferTps = seconds > 0? count / seconds : count;
    }

    public int getCount() {
        return count;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getTransferTps() {
        return transferTps;
    }
}
