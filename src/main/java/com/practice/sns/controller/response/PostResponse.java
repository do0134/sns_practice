package com.practice.sns.controller.response;

import com.practice.sns.model.Post;
import com.practice.sns.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;



import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class PostResponse {
    private Long id;

    private String title;

    private String body;

    private UserResponse user;

    private Timestamp registerAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                UserResponse.fromUser(post.getUser()),
                post.getRegisterAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );
    }

}
