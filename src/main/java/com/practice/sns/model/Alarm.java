package com.practice.sns.model;

import com.practice.sns.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;


@Slf4j
@Getter
@AllArgsConstructor
public class Alarm {
    private Long id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private Timestamp registerAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity e) {
        log.info("================call entity==============");
        return new Alarm(
                e.getId(),
                e.getAlarmType(),
                e.getArgs(),
                e.getRegisterAt(),
                e.getUpdatedAt(),
                e.getDeletedAt()
        );
    }
}
