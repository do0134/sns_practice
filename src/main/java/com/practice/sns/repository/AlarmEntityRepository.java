package com.practice.sns.repository;

import com.practice.sns.model.entity.AlarmEntity;
import com.practice.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Long> {
    Page<AlarmEntity> findAllByUserId(Long userId, Pageable pageable);

//    @Query(value = "SELECT COUNT (*) FROM LikeEntity entity where entity.post =:post")
//    Integer countByPost(@Param("post") PostEntity post);
//
//    List<LikeEntity> findAllByPost(PostEntity post);
}
