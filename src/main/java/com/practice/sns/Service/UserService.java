package com.practice.sns.Service;

import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.User;
import com.practice.sns.model.entity.UserEntity;
import com.practice.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
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
        Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);
        return new User();
    }

    /**
     * login Implement
     * @return
     */
    public String login(String userName, String password) {
       UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SNSApplicationException());

       if (!userEntity.getPassword().equals(password)) {
           throw new SNSApplicationException();
       }

        return "Success";
    }
}
