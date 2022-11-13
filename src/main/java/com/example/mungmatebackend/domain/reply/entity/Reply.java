package com.example.mungmatebackend.domain.reply.entity;

import com.example.mungmatebackend.domain.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "comment_id")
  private Long commentId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Long postId;

  @Column(nullable = false)
  private String content;

}
