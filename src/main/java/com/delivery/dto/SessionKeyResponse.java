package com.delivery.dto;

public class SessionKeyResponse {
    public String getEncryptedSessionKey() {
        return encryptedSessionKey;
    }

    public void setEncryptedSessionKey(String encryptedSessionKey) {
        this.encryptedSessionKey = encryptedSessionKey;
    }

    private String encryptedSessionKey;
}
