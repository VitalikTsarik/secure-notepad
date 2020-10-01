package com.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionKeyRequest {
    private String openRSAkey;
}
