package com.delivery.dto;

import com.delivery.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private long id;
    private String login;

    public static UserDto build(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin()
        );
    }
}
