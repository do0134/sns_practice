package com.practice.sns.controller.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
    private String title;
    private String body;

    @Builder
    public PostCreateRequest(String title,String body) {
        this.title = title;
        this.body = body;
    }
}
