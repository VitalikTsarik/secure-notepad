package com.delivery.dto;

import com.delivery.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
	private final String tokenType = "Bearer";
	private String token;
	private String login;
	private Role role;
}
