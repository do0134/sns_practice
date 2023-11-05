package com.practice.sns.Service;

import com.practice.sns.exception.ErrorCode;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.User;
import com.practice.sns.model.entity.UserEntity;
import com.practice.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;


    /**
     * join Implement
     */
    public User join(String userName, String password) {
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SNSApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated",userName));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,password));
        return User.fromEntity(userEntity);
    }

    /**
     * login Implement
     * @return
     */
    public String login(String userName, String password) {
       UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SNSApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated", userName)));


       if (!userEntity.getPassword().equals(password)) {
           throw new SNSApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated", userName));
       }

        return "Success";
    }
}
