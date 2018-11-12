package com.spartos.testclient.network;

import com.spartos.testclient.credentials.CredentialsGroup;
import org.web3j.protocol.core.methods.response.EthGetBalance;

public class ParticipantInfoResponse {

    private CredentialsGroup group;
    private String address;
    private EthGetBalance balance;

    public ParticipantInfoResponse(CredentialsGroup group, String address, EthGetBalance balance) {
        this.group = group;
        this.address = address;
        this.balance = balance;
    }

    public CredentialsGroup getGroup() {
        return group;
    }

    public String getAddress() {
        return address;
    }

    public EthGetBalance getBalance() {
        return balance;
    }
}
