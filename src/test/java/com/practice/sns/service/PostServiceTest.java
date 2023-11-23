package com.practice.sns.service;

import com.practice.sns.Service.PostService;
import com.practice.sns.exception.ErrorCode;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.User;
import com.practice.sns.model.entity.PostEntity;
import com.practice.sns.model.entity.UserEntity;
import com.practice.sns.repository.PostEntityRepository;
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
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostEntityRepository postEntityRepository;
    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 포스트작성이_성공한경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        Assertions.assertDoesNotThrow(() -> postService.create(title,body,userName));

    }

    @Test
    void 포스트작성시_요청한유저가_존재하지않을경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SNSApplicationException e = Assertions.assertThrows(SNSApplicationException.class, () -> postService.create(title, body, userName));

        Assertions.assertDoesNotThrow(() -> postService.create(title,body,userName));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());

    }


}
