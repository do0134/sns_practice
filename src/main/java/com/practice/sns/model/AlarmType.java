package com.practice.sns.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

@RequiredArgsConstructor
@Getter
public enum AlarmType {
    NEW_COMMNET_ON_POST("new_comment"),
    NEW_LIKE_ON_POST("new_like"),
    ;

    private final String alarmText;
}
