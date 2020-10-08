package com.delivery.repository;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class SessionsRepo {

    public Map<String, SecretKey> getSecretKeyMap() {
        return secretKeyMap;
    }

    public void setSecretKeyMap(Map<String, SecretKey> secretKeyMap) {
        this.secretKeyMap = secretKeyMap;
    }

    private Map<String, SecretKey> secretKeyMap = new LinkedHashMap<String, SecretKey>();
}
