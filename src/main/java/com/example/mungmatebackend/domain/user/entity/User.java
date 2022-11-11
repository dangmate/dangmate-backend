package com.example.mungmatebackend.domain.user.entity;

import com.example.mungmatebackend.domain.likeUser.entity.LikeUser;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.example.mungmatebackend.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  private String password;

  @Column(length = 200, unique = true)
  private String fullName;

  @Column(length = 20, nullable = false)
  private String location;

  private String profile;

  @OneToMany(mappedBy = "user")
  private List<LikeUser> likeUsers = new ArrayList<>();
}
