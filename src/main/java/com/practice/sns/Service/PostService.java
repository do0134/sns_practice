package com.practice.sns.Service;

import com.practice.sns.exception.ErrorCode;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.Post;
import com.practice.sns.model.entity.PostEntity;
import com.practice.sns.model.entity.UserEntity;
import com.practice.sns.repository.PostEntityRepository;
import com.practice.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        UserEntity user = userEntityRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new SNSApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName))
                );

        PostEntity postEntity = postEntityRepository.save(PostEntity.of(title, body, user));
    }

    @Transactional
    public Post modify(String title, String body, String userName, Long postId) {
        UserEntity user = userEntityRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new SNSApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName))
                );

        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new SNSApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId))
        );

        if (postEntity.getUser() != user) {
            throw new SNSApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    public void delete(String name, Long postId) {
        UserEntity user = userEntityRepository.findByUserName(name).orElseThrow(() ->
                new SNSApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", name))
        );

        PostEntity post = postEntityRepository.findById(postId).orElseThrow(() ->
                new SNSApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s is not founded", postId))
        );

        if (post.getUser() != user) {
            throw new SNSApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no perssion with %s", name, postId)
            );
        }

        postEntityRepository.delete(post);
    }

    public Page<Post> list(Pageable pageable) {
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> myList(String userName, Pageable pageable) {
        UserEntity user = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SNSApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", userName))
        );
        return postEntityRepository.findAllByUser(user, pageable).map(Post::fromEntity);
    }
}
