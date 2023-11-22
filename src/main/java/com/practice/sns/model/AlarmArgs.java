package com.practice.sns.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlarmArgs {

    private Long fromUserId;
    private Long targetId;

    @Builder
    public AlarmArgs(Long fromUserId, Long targetId) {
        this.fromUserId = fromUserId;
        this.targetId = targetId;
    }

}
