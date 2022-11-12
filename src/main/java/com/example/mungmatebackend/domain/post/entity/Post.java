package com.example.mungmatebackend.domain.post.entity;

import com.example.mungmatebackend.domain.common.BaseEntity;
import com.example.mungmatebackend.domain.galleryPost.entity.GalleryPost;
import com.example.mungmatebackend.domain.likeUser.entity.LikeUser;
import com.example.mungmatebackend.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(length = 20, nullable = false)
  private String location;

  @Column(nullable = false, length = 20)
  private String category;

  private String thumbnail;

  private String content;

  @ColumnDefault("0")
  private Integer likes;

  @ColumnDefault("0")
  private Integer views;

  @ColumnDefault("0")
  private Integer comments;

  @OneToMany(mappedBy = "post")
  @Builder.Default
  private List<LikeUser> likeUsers = new ArrayList<>();

  @OneToMany(mappedBy = "post")
  @Builder.Default
  private List<GalleryPost> galleryPosts = new ArrayList<>();

}
