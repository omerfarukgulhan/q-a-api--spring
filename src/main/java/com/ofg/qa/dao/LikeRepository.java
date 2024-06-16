package com.ofg.qa.dao;

import com.ofg.qa.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);

    @Query("FROM Like l WHERE l.post.id = :postId")
    List<Like> findLikesByPostId(@Param("postId") Long postId);
}