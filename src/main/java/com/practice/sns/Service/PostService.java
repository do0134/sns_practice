package com.practice.sns.Service;

import com.practice.sns.exception.ErrorCode;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.model.AlarmArgs;
import com.practice.sns.model.AlarmType;
import com.practice.sns.model.Comment;
import com.practice.sns.model.Post;
import com.practice.sns.model.entity.*;
import com.practice.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeEntityRepository;
    private final CommentEntityRepository commentEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;

    private PostEntity getPostOrException(Long postId) {
        return postEntityRepository.findById(postId).orElseThrow(() ->
                new SNSApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s is not founded", postId))
        );
    }

    private UserEntity getUserOrException(String userName) {
        return userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SNSApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", userName))
        );
    }

    @Transactional
    public void create(String title, String body, String userName) {
        UserEntity user = getUserOrException(userName);

        PostEntity postEntity = postEntityRepository.save(PostEntity.of(title, body, user));
    }

    @Transactional
    public Post modify(String title, String body, String userName, Long postId) {
         UserEntity user = getUserOrException(userName);

        PostEntity postEntity = getPostOrException(postId);

        if (postEntity.getUser() != user) {
            throw new SNSApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    public void delete(String name, Long postId) {
        UserEntity user = getUserOrException(name);

        PostEntity post = getPostOrException(postId);

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
        UserEntity user = getUserOrException(userName);
        return postEntityRepository.findAllByUser(user, pageable).map(Post::fromEntity);
    }

    @Transactional
    public void like(Long postId, String userName) {
        UserEntity user = getUserOrException(userName);

        PostEntity post = getPostOrException(postId);

        likeEntityRepository.findByUserAndPost(user, post).ifPresent(it -> {
            throw new SNSApplicationException(ErrorCode.ALREADY_LIKED, String.format("userName %s already like post %d",userName,postId));
        });

        likeEntityRepository.save(LikeEntity.of(post,user));

        alarmEntityRepository.save(AlarmEntity.of(post.getUser(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(user.getId(),post.getId())));
    }

    @Transactional
    public Long likeCount(Long postId) {
        PostEntity post = getPostOrException(postId);

        Long likes = likeEntityRepository.countByPost(post);
        // Integer likes = likeEntityRepository.findAllByPost(post).size();
        return likes;
    }

    @Transactional
    public void comment(Long postId, String userName, String comment) {
        PostEntity post = getPostOrException(postId);
        UserEntity user = getUserOrException(userName);
        commentEntityRepository.save(CommentEntity.of(post,user,comment));

        alarmEntityRepository.save(AlarmEntity.of(post.getUser(), AlarmType.NEW_COMMNET_ON_POST, new AlarmArgs(user.getId(),post.getId())));
    }

    public Page<Comment> getComments(Long postId, Pageable pageable) {
        PostEntity post = getPostOrException(postId);
        return commentEntityRepository.findAllByPost(post, pageable).map(Comment::fromEntity);
    }
}
