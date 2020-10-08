package com.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextWithIdDTO {
    private Long id;
    private String encryptedText;
    private String encryptedSessionKey;
}
