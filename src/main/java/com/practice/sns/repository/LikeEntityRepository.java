package com.practice.sns.repository;

import com.practice.sns.model.entity.LikeEntity;
import com.practice.sns.model.entity.PostEntity;
import com.practice.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    Long countByPost(PostEntity post);

    @Transactional
    @Modifying
    @Query("UPDATE LikeEntity entity SET entity.deletedAt = NOW() where entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity post);

//    @Query(value = "SELECT COUNT (*) FROM LikeEntity entity where entity.post =:post")
//    Integer countByPost(@Param("post") PostEntity post);
//
//    List<LikeEntity> findAllByPost(PostEntity post);
}
