package com.spartos.testclient.network;

import com.spartos.testclient.bets.TransactionsController;
import com.spartos.testclient.credentials.CredentialsGroup;
import com.spartos.testclient.credentials.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ParticipantsInfoController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsController.class);

    private CredentialsProvider credentialsProvider;

    @Autowired
    public ParticipantsInfoController(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    @GetMapping("/participantsInfo")
    public List<ParticipantInfoResponse> getInfo(@RequestParam("nodeUrl") String nodeUrl) throws Exception {

        Web3j node = Web3j.build(new HttpService("http://" + nodeUrl));
        List<ParticipantInfoResponse> participantsInfo = new ArrayList<>();

        Credentials ownerCredentials = credentialsProvider.loadCredentials(CredentialsGroup.OWNER);
        EthGetBalance ownerBalance = node
                .ethGetBalance(ownerCredentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send();

        participantsInfo.add(new ParticipantInfoResponse(CredentialsGroup.OWNER, ownerCredentials.getAddress(), ownerBalance));

        Credentials operatorCredentials = credentialsProvider.loadCredentials(CredentialsGroup.OPERATOR);
        EthGetBalance operatorBalance = node
                .ethGetBalance(operatorCredentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send();

        participantsInfo.add(new ParticipantInfoResponse(CredentialsGroup.OPERATOR, operatorCredentials.getAddress(), operatorBalance));

        return participantsInfo;
    }
}
