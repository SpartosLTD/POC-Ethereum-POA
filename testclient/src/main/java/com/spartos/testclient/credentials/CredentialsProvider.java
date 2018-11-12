package com.spartos.testclient.credentials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CredentialsProvider {

    private static final Logger LOG = LoggerFactory.getLogger(CredentialsProvider.class);

    public Credentials loadCredentials(CredentialsGroup group)  {
        return loadCredentials(group, 1).get(0);
    }

    public List<Credentials> loadCredentials(CredentialsGroup group, int count) {
        switch (group) {
            case OWNER:
                return Collections.singletonList(loadCredentialsFromResource("owner/keystore/owner.json", "passwd"));
            case OPERATOR:
                return Collections.singletonList(loadCredentialsFromResource("operator/keystore/operator.json", "passwd"));
            case PLAYER:
            default:
                return generateCredentials(count);
        }

    }

    private Credentials loadCredentialsFromResource(String resource, String password) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(resource).getFile());
            return WalletUtils.loadCredentials(password, file);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    private List<Credentials> generateCredentials(int countNeeded) {

        return IntStream.range(0, countNeeded)
                .parallel()
                .mapToObj(i -> {
                            try {
                                ECKeyPair ecKeyPair = Keys.createEcKeyPair();
                                return Credentials.create(ecKeyPair);
                            } catch (Exception e) {
                                LOG.error(e.getMessage());
                            }
                            return null;
                        })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
