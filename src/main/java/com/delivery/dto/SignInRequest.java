package com.delivery.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInRequest {
	@NotBlank(message = "Login shouldn't be blank")
	private String login;

	@NotBlank(message = "Password shouldn't be blank")
	private String password;
}
