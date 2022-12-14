package com.example.mungmatebackend.domain.comment.repository;

import com.example.mungmatebackend.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPostId(Long postId);
  List<Comment> findByUserIdOrderByIdDesc(Long userId);
}
