package com.practice.sns.controller.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostModifyRequest {
    String title;
    String body;

    @Builder
    public PostModifyRequest(String title,String body) {
        this.title = title;
        this.body = body;
    }
}
