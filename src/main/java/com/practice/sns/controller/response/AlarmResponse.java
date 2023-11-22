package com.practice.sns.controller.response;

import com.practice.sns.model.Alarm;
import com.practice.sns.model.AlarmArgs;
import com.practice.sns.model.AlarmType;
import com.practice.sns.model.User;
import com.practice.sns.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;


@Data
@Getter
@AllArgsConstructor
public class AlarmResponse {
    private Long id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private String text;
    private Timestamp registerAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static AlarmResponse fromAlarm(Alarm e) {
        return new AlarmResponse(
                e.getId(),
                e.getAlarmType(),
                e.getAlarmArgs(),
                e.getAlarmType().getAlarmText(),
                e.getRegisterAt(),
                e.getUpdatedAt(),
                e.getDeletedAt()
        );
    }
}
