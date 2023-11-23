package com.practice.sns.Service;

import com.practice.sns.exception.ErrorCode;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.Alarm;
import com.practice.sns.model.User;
import com.practice.sns.model.entity.UserEntity;
import com.practice.sns.repository.AlarmEntityRepository;
import com.practice.sns.repository.UserCacheRepository;
import com.practice.sns.repository.UserEntityRepository;
import com.practice.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;
    private final AlarmEntityRepository alarmEntityRepository;
    private final UserCacheRepository userCacheRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

//    private User getUserOrException(String userName) {
//        return userCacheRepository.getUser(userName).orElseGet(() ->
//                userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
//                        new SNSApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", userName))
//                ));
//    }

    public User loadUserByUserName(String userName) {
        System.out.println(userName+ "111111111111111111111111111");
        return userCacheRepository.getUser(userName).orElseGet(() ->
                userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
                        new SNSApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", userName))));
    }

    /**
     * join Implement
     */
    @Transactional
    public User join(String userName, String password) {
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SNSApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated",userName));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));
        return User.fromEntity(userEntity);
    }

    /**
     * login Implement
     * @return
     */
    public String login(String userName, String password) {
       User user = loadUserByUserName(userName);
        userCacheRepository.setUser(user);

       if (!encoder.matches(password, user.getPassword())) {
           throw new SNSApplicationException(ErrorCode.INVALID_PASSWORD);
       }

        String token = JwtTokenUtils.generateToken(userName, secretKey,expiredTimeMs);
        return token;
    }

    public Page<Alarm> alarmList(Long userId, Pageable pageable) {
        return alarmEntityRepository.findAllByUserId(userId,pageable).map(Alarm::fromEntity);
    }
}
