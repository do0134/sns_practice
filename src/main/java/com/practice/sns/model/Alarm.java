package com.practice.sns.model;

import com.practice.sns.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;


@Getter
@AllArgsConstructor
public class Alarm {
    private Long id;
    private User user;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private Timestamp registerAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity e) {
        return new Alarm(
                e.getId(),
                User.fromEntity(e.getUser()),
                e.getAlarmType(),
                e.getArgs(),
                e.getRegisterAt(),
                e.getUpdatedAt(),
                e.getDeletedAt()
        );
    }
}
