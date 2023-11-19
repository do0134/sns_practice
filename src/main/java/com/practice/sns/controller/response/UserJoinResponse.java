package com.practice.sns.controller.response;

import com.practice.sns.model.User;
import com.practice.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinResponse {

    private Long id;
    private String userName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user) {
        return new UserJoinResponse(
            user.getId(),
                user.getUsername(),
                user.getUserRole()
        );
    }

}
