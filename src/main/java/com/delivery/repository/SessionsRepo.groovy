package com.delivery.repository

import org.springframework.stereotype.Component

import javax.crypto.SecretKey

@Component
class SessionsRepo {
    Map<String, SecretKey> secretKeyMap = [:]
}
