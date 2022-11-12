package com.example.mungmatebackend.domain.likeUser.service;

import com.example.mungmatebackend.api.post.dto.LikeDto;
import com.example.mungmatebackend.domain.likeUser.entity.LikeUser;
import com.example.mungmatebackend.domain.likeUser.repository.LikeUserRepository;
import com.example.mungmatebackend.domain.post.entity.Post;
import com.example.mungmatebackend.domain.post.repository.PostRepository;
import com.example.mungmatebackend.domain.user.entity.User;
import com.example.mungmatebackend.domain.user.repository.UserRepository;
import com.example.mungmatebackend.global.error.ErrorCode;
import com.example.mungmatebackend.global.error.exception.BusinessException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeUserService {

  private final LikeUserRepository likeUserRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public LikeDto.LikeResponseDto like(LikeDto.LikeRequestDto request) {
    Optional<Post> post = postRepository.findById(request.getPostId());
    Optional<User> user = userRepository.findById(request.getUserId());

    if (post.isEmpty()) {
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }
    if (user.isEmpty()) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    post.get().setLikes(post.get().getLikes() + 1);
    postRepository.save(post.get());

    likeUserRepository.save(LikeUser.builder()
        .post(post.get())
        .user(user.get())
        .build());

    return LikeDto.LikeResponseDto.builder()
        .statusCode("200")
        .userId(request.getUserId())
        .postId(request.getPostId())
        .build();
  }

  public LikeDto.LikeResponseDto unlike(LikeDto.LikeRequestDto request) {
    Optional<Post> post = postRepository.findById(request.getPostId());
    Optional<User> user = userRepository.findById(request.getUserId());

    if (post.isEmpty()) {
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }
    if (user.isEmpty()) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    post.get().setLikes(post.get().getLikes() - 1);
    postRepository.save(post.get());

    likeUserRepository.deleteByPostIdAndUserId(post.get().getId(), user.get().getId());

    return LikeDto.LikeResponseDto.builder()
        .statusCode("200")
        .userId(request.getUserId())
        .postId(request.getPostId())
        .build();
  }

}
