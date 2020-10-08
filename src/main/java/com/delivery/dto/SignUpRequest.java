package com.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Login shouldn't be blank")
    private String login;

    @NotBlank(message = "Password shouldn't be blank")
    private String password;

    private String encryptedSessionKey;
}
