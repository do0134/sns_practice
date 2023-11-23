package com.practice.sns.controller;

import com.practice.sns.Service.AlarmService;
import com.practice.sns.Service.UserService;
import com.practice.sns.controller.request.UserJoinRequest;
import com.practice.sns.controller.request.UserLoginRequest;
import com.practice.sns.controller.response.AlarmResponse;
import com.practice.sns.controller.response.Response;
import com.practice.sns.controller.response.UserJoinResponse;
import com.practice.sns.controller.response.UserLoginResponse;
import com.practice.sns.exception.ErrorCode;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.Alarm;
import com.practice.sns.model.User;
import com.practice.sns.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AlarmService alarmService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(),User.class).orElseThrow(() -> new SNSApplicationException(ErrorCode.INTERNAL_SERVER_ERROR,"Casting to User class failed"));
        return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
    }

    @GetMapping("/alarm/subscribe")
    public SseEmitter subscribe(Authentication authentication){
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(),User.class).orElseThrow(() -> new SNSApplicationException(ErrorCode.INTERNAL_SERVER_ERROR,"Casting to User class failed"));
        return alarmService.connectAlarm(user.getId());
    }

}
