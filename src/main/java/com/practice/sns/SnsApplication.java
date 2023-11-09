package com.practice.sns;

import com.practice.sns.Service.UserService;
import com.practice.sns.controller.request.UserJoinRequest;
import com.practice.sns.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication()
public class SnsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SnsApplication.class, args);
	}

}
