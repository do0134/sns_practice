package com.practice.sns.service;

import com.practice.sns.Service.UserService;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.entity.UserEntity;
import com.practice.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        String userName = "userName";
        String password = "password";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertDoesNotThrow(() -> userService.join(userName,password));
    }

    @Test
    void 회원가입시_username으로_가입한_유저가_있는경우() {
        String userName = "userName";
        String password = "password";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertThrows(SNSApplicationException.class,() -> userService.join(userName,password));
    }

    @Test
    void 로그인_정상적으로_동작하는_경우() {
        String userName = "userName";
        String password = "password";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertDoesNotThrow(() -> userService.login(userName,password));
    }

    @Test
    void 로그인시_username으로_가입한_유저가_있는경우() {
        String userName = "userName";
        String password = "password";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertThrows(SNSApplicationException.class,() -> userService.join(userName,password));
    }
}
