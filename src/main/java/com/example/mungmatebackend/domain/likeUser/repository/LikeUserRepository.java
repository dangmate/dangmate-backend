package com.example.mungmatebackend.domain.likeUser.repository;

import com.example.mungmatebackend.domain.likeUser.entity.LikeUser;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikeUserRepository extends JpaRepository<LikeUser,Long> {
  Optional<LikeUser> findByPostIdAndUserId(Long postId, Long userId);
  @Transactional
  Optional<LikeUser> deleteByPostIdAndUserId(Long postId, Long userId);

  List<LikeUser> findByUserId(Long userId);
}
