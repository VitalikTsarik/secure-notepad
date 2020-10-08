package com.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
	@NotBlank(message = "Login shouldn't be blank")
	private String login;

	@NotBlank(message = "Password shouldn't be blank")
	private String password;
}
