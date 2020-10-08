package com.delivery.repository;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionsRepo {

    private Map<String, SecretKey> secretKeyMap = new ConcurrentHashMap<>();
    private Map<String, String> rsaKeysBySecretKey = new ConcurrentHashMap<>();
    private Map<String, Long> userBySecretKey = new ConcurrentHashMap<>();


    public Map<String, SecretKey> getSecretKeyMap() {
        return secretKeyMap;
    }

    public Map<String, String> getRsaKeysBySecretKey() {
        return rsaKeysBySecretKey;
    }

    public Map<String, Long> getUserBySecretKey() {
        return userBySecretKey;
    }
}
