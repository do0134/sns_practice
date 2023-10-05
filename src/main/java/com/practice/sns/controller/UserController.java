package com.practice.sns.controller;

import com.practice.sns.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void join() {
        userService.join("", "");
    }

    @PostMapping
    public void login() {
        userService.login("","");
    }
}
