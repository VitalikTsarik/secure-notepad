package com.delivery.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	CARGO_OWNER,
    TRANSPORTER,
    MANAGER,
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
