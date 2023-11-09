package com.practice.sns.controller.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequest {
    private String name;
    private String password;

    @Builder
    public UserJoinRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
