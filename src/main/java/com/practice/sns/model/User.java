package com.practice.sns.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User {
    private Long id;
    private String userName;
    private String password;
    private UserRole userRole;
    private Timestamp registerAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static User fromEntity(UserEntity entity) {
        return new User(
            entity.getId(),
            entity.getUserName(),
            entity.getPassword(),
            entity.getRole(),
            entity.getRegisterAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
            );
    }
}
