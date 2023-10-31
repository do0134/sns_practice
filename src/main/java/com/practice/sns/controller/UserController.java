package com.practice.sns.controller;

import com.practice.sns.Service.UserService;
import com.practice.sns.controller.request.UserJoinRequest;
import com.practice.sns.controller.response.Response;
import com.practice.sns.controller.response.UserJoinResponse;
import com.practice.sns.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getUserName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping
    public void login() {
        userService.login("","");
    }
}
