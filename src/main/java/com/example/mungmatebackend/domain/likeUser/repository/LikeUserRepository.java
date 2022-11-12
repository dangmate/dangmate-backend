package com.example.mungmatebackend.domain.likeUser.repository;

import com.example.mungmatebackend.domain.likeUser.entity.LikeUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeUserRepository extends JpaRepository<LikeUser,Long> {
  Optional<LikeUser> findByPostIdAndUserId(Long postId, Long userId);
}
