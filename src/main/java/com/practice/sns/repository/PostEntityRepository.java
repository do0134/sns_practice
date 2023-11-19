package com.practice.sns.repository;

import com.practice.sns.model.Post;
import com.practice.sns.model.entity.PostEntity;
import com.practice.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long>{

    Page<PostEntity> findAllByUser(UserEntity entity, Pageable pageable);
}
