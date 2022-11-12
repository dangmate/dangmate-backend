package com.example.mungmatebackend.domain.post.service;

import com.example.mungmatebackend.api.post.dto.PostDto;
import com.example.mungmatebackend.api.posts.dto.PostsDto;
import com.example.mungmatebackend.domain.likeUser.entity.LikeUser;
import com.example.mungmatebackend.domain.likeUser.repository.LikeUserRepository;
import com.example.mungmatebackend.domain.post.entity.Post;
import com.example.mungmatebackend.domain.post.repository.PostRepository;
import com.example.mungmatebackend.domain.user.entity.User;
import com.example.mungmatebackend.domain.user.repository.UserRepository;
import com.example.mungmatebackend.global.error.ErrorCode;
import com.example.mungmatebackend.global.error.exception.BusinessException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final LikeUserRepository likeUserRepository;

  private PostsDto.PostsResponse getAllList(PostsDto.PostsRequest request) {
    List<Post> posts = postRepository.findAll(Sort.by(Direction.DESC, "id"));

    for (Post post : posts) {
      User user = post.getUser();
      String profile = user.getProfile();
      String fullName = user.getFullName();
      String category = post.getCategory();
      String thumbnail = post.getThumbnail();
      String content = post.getContent();
      String location = post.getLocation();
      LocalDateTime createdAt = post.getCreatedAt();
      Integer views = post.getViews();

    }

//    PostDto.builder()
//        .profile()
//        .build();

//    return PostsDto.PostsResponse.builder()
//        .location(request.getLocation())
//        .posts(
//            List < PostDto > PostDto.builder().build();
//        )
//        .total(posts.size())
//        .build();

    return PostsDto.PostsResponse.builder().build();
  }

  public PostsDto.PostsResponse getPosts(PostsDto.PostsRequest request) {

    String category = request.getCategory();
    String location = request.getLocation();

    if (category.equals("all")) {
      return getAllList(request);
    }

    List<Post> posts = postRepository.findByCategory(category);

    return PostsDto.PostsResponse.builder().build();
  }

  public com.example.mungmatebackend.api.post.dto.PostDto.PostUploadResponse uploadPost(
      com.example.mungmatebackend.api.post.dto.PostDto.PostUploadRequest request) {

    Post post = postRepository.save(Post.builder()
        .location(request.getLocation())
        .category(request.getCategory())
        .thumbnail(request.getThumbnail())
        .content(request.getContent())
        .user(User.builder()
            .id(request.getUserId())
            .build())
        .build());

    return com.example.mungmatebackend.api.post.dto.PostDto.PostUploadResponse.builder()
        .statusCode("200")
        .postId(post.getId())
        .build();
  }

  public PostDto.PostGetResponse getPost(Long id){
    Optional<Post> post = postRepository.findById(id);

    if(post.isEmpty()){
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }

    Optional<User> user = userRepository.findById(post.get().getUser().getId());

    String createdAt = "";
    LocalDateTime currentTime = LocalDateTime.now();
    LocalDateTime savedTime = post.get().getCreatedAt();

    if (ChronoUnit.YEARS.between(savedTime, currentTime) >= 1) {
      int year = (int) ChronoUnit.YEARS.between(savedTime, currentTime);
      createdAt = year + "년 전";
    }else if(ChronoUnit.MONTHS.between(savedTime, currentTime) >= 1){
      int month = (int) ChronoUnit.MONTHS.between(savedTime, currentTime);
      createdAt = month + "달 전";
    }else if(ChronoUnit.DAYS.between(savedTime, currentTime) >= 1){
      int day = (int) ChronoUnit.DAYS.between(savedTime, currentTime);
      createdAt = day + "일 전";
    }else if(ChronoUnit.HOURS.between(savedTime, currentTime) >= 1){
      int hour = (int) ChronoUnit.HOURS.between(savedTime, currentTime);
      createdAt = hour + "시간 전";
    }else if(ChronoUnit.MINUTES.between(savedTime, currentTime) >= 1){
      int minute = (int) ChronoUnit.MINUTES.between(savedTime, currentTime);
      createdAt = minute + "분 전";
    }else{
      createdAt = "방금 전";
    }

    boolean isLike = false;
    Optional<LikeUser> likeUser = likeUserRepository.findByPostIdAndUserId(post.get().getId(), user.get().getId());
    if(likeUser.isPresent()){
      isLike = true;
    }

    int views = post.get().getViews();

    post.get().setViews(post.get().getViews() + 1);
    postRepository.save(post.get());

    return PostDto.PostGetResponse.builder()
        .profile(user.get().getProfile())
        .fullName(user.get().getFullName())
        .category(post.get().getCategory())
        .thumbnail(post.get().getThumbnail())
        .content(post.get().getContent())
        .location(post.get().getLocation())
        .createdAt(createdAt)
        .comments(post.get().getComments())
        .likes(post.get().getLikes())
        .isLike(isLike)
        .views(views)
        .build();
  }

}
