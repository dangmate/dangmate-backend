package com.example.mungmatebackend.domain.gallery.entity;

import com.example.mungmatebackend.domain.common.BaseEntity;
import com.example.mungmatebackend.domain.galleryPost.entity.GalleryPost;
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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Gallery extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, unique = true)
  private String url;

  @OneToMany(mappedBy = "gallery")
  @Builder.Default
  private List<GalleryPost> galleryPosts = new ArrayList<>();

}
