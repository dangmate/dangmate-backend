package com.example.mungmatebackend.domain.comment.entity;

import com.example.mungmatebackend.domain.common.BaseEntity;
import com.example.mungmatebackend.domain.post.entity.Post;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;

@Entity
@Builder
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

}
