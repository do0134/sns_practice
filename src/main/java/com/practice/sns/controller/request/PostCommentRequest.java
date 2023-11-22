package com.practice.sns.controller.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Getter
public class PostCommentRequest {
    private String comment;

    @Builder
    public PostCommentRequest(String comment) {
        this.comment = comment;
    }
}
