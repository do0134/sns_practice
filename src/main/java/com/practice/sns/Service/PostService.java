package com.practice.sns.Service;

import com.practice.sns.exception.ErrorCode;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.User;
import com.practice.sns.model.entity.PostEntity;
import com.practice.sns.model.entity.UserEntity;
import com.practice.sns.repository.PostEntityRepository;
import com.practice.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        //find
        UserEntity user = userEntityRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new SNSApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName))
                );

        // post create
        postEntityRepository.save(new PostEntity());

        // save
    }
}
