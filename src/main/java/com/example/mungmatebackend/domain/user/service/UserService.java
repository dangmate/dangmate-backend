package com.example.mungmatebackend.domain.user.service;

import com.example.mungmatebackend.api.user.login.dto.UserDto;
import com.example.mungmatebackend.api.user.login.dto.UserDto.deleteProfileResponse;
import com.example.mungmatebackend.api.user.login.dto.request.UserLoginReq;
import com.example.mungmatebackend.api.user.login.dto.response.UserLoginRes;
import com.example.mungmatebackend.api.user.signin.dto.request.UserEmailReq;
import com.example.mungmatebackend.api.user.signin.dto.request.UserFullNameReq;
import com.example.mungmatebackend.api.user.signin.dto.request.UserSigninReq;
import com.example.mungmatebackend.api.user.signin.dto.response.UserEmailRes;
import com.example.mungmatebackend.api.user.signin.dto.response.UserFullNameRes;
import com.example.mungmatebackend.api.user.signin.dto.response.UserSigninRes;
import com.example.mungmatebackend.domain.comment.entity.Comment;
import com.example.mungmatebackend.domain.comment.repository.CommentRepository;
import com.example.mungmatebackend.domain.likeUser.entity.LikeUser;
import com.example.mungmatebackend.domain.likeUser.repository.LikeUserRepository;
import com.example.mungmatebackend.domain.post.entity.Post;
import com.example.mungmatebackend.domain.post.repository.PostRepository;
import com.example.mungmatebackend.domain.reply.entity.Reply;
import com.example.mungmatebackend.domain.reply.repository.ReplyRepository;
import com.example.mungmatebackend.domain.user.entity.User;
import com.example.mungmatebackend.domain.user.repository.UserRepository;
import com.example.mungmatebackend.global.error.ErrorCode;
import com.example.mungmatebackend.global.error.exception.BusinessException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final ReplyRepository replyRepository;
  private final LikeUserRepository likeUserRepository;
  public UserLoginRes login(UserLoginReq userLoginReq){
    Optional<User> user = userRepository.findByEmail(userLoginReq.getEmail());

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.EMAIL_NOT_FOUND);
    }

    if(!passwordEncoder.matches(userLoginReq.getPassword(), user.get().getPassword())){
      throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    return UserLoginRes.builder()
        .statusCode("200")
        .email(user.get().getEmail())
        .fullName(user.get().getFullName())
        .location(user.get().getLocation())
        .userId(user.get().getId())
        .build();
  }

  public UserSigninRes signin(UserSigninReq userSigninReq){

    User newUser = User.builder()
            .email(userSigninReq.getEmail())
            .password(passwordEncoder.encode(userSigninReq.getPassword()))
            .fullName(userSigninReq.getFullName())
            .location(userSigninReq.getLocation())
            .build();

    userRepository.save(newUser);

    return UserSigninRes.builder()
            .statusCode("200")
            .email(userSigninReq.getEmail())
            .fullName(userSigninReq.getFullName())
            .location(userSigninReq.getLocation())
            .build();
  }

  public UserEmailRes signinEmail(UserEmailReq userEmailReq){
    Optional<User> user = userRepository.findByEmail(userEmailReq.getEmail());

    if(user.isPresent()){
      throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXIST);
    }

    return UserEmailRes.builder()
            .statusCode("200")
            .email(userEmailReq.getEmail())
            .build();
  }

  public UserFullNameRes signinFullName(UserFullNameReq userFullNameReq){
    Optional<User> user = userRepository.findByFullName(userFullNameReq.getFullName());

    if(user.isPresent()){
      throw new BusinessException(ErrorCode.FULL_NAME_ALREADY_EXIST);
    }

    return UserFullNameRes.builder()
            .statusCode("200")
            .fullName(userFullNameReq.getFullName())
            .build();
  }

  public UserDto.getProfileResponse getProfile(Long userId){
    Optional<User> user = userRepository.findById(userId);
    List<Post> posts =  postRepository.findAllByUserIdAndIsActiveOrderByIdDesc(userId, true);
    List<Comment> comments = commentRepository.findByUserIdOrderByIdDesc(userId);

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    return UserDto.getProfileResponse.builder()
            .userId(userId)
            .profile(user.get().getProfile())
            .fullName(user.get().getFullName())
            .posts(posts.size())
            .comments(comments.size())
            .location(user.get().getLocation())
            .users(userRepository.findByLocation(user.get().getLocation()).size())
            .build();
  }

  private boolean isUpdatedNotAfter7Days(LocalDateTime pastTIme){
    LocalDateTime currentTime = LocalDateTime.now();

    if(ChronoUnit.DAYS.between(pastTIme, currentTime) <= 7){
      return true;
    }

    return false;
  }
  public UserDto.updateProfileResponse updateProfile(
      Long userId,
      UserDto.updateProfileRequest request
  ){
    Optional<User> user = userRepository.findById(userId);

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    if(isUpdatedNotAfter7Days(user.get().getUpdatedAt())
      && !user.get().getCreatedAt().equals(user.get().getUpdatedAt())
    ){
      throw new BusinessException(ErrorCode.UPDATED_WITHIN_7_DAYS);
    }

    user.get().setProfile(request.getProfile());
    user.get().setFullName(request.getFullName());
    userRepository.save(user.get());

    return UserDto.updateProfileResponse.builder()
        .profile(request.getProfile())
        .fullName(request.getFullName())
        .build();
  }

  public UserDto.updateCheckProfileResponse updateCheckProfile(Long userId){
    Optional<User> user = userRepository.findById(userId);

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    if(user.get().getCreatedAt().equals(user.get().getUpdatedAt())){
      return UserDto.updateCheckProfileResponse.builder()
              .canBeUpdated(true)
              .build();
    }

    if(isUpdatedNotAfter7Days(user.get().getUpdatedAt())){
      return UserDto.updateCheckProfileResponse.builder()
              .canBeUpdated(false)
              .build();
    }

    return UserDto.updateCheckProfileResponse.builder()
            .canBeUpdated(true)
            .build();

  }

  public UserDto.deleteProfileResponse deleteProfile(Long userId){

    Optional<User> user = userRepository.findById(userId);
    List<LikeUser> likeUsers = likeUserRepository.findByUserId(userId);
    List<Reply> replies = replyRepository.findByUserId(userId);
    List<Comment> comments = commentRepository.findByUserIdOrderByIdDesc(userId);
    List<Post> posts = postRepository.findByUserId(userId);

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    for(LikeUser likeUser: likeUsers){
      likeUserRepository.delete(likeUser);
      Optional<Post> post = postRepository.findById(likeUser.getPost().getId());
      post.get().setLikes(post.get().getLikes() - 1);
      postRepository.save(post.get());
    }

    for(Reply reply: replies){
      replyRepository.delete(reply);
    }

    for(Comment comment: comments){
      if(comment.getReply()>0){
        List<Reply> commentReplies = replyRepository.findByCommentId(comment.getId());
        for(Reply reply: commentReplies){
          replyRepository.delete(reply);
          Optional<Post> post = postRepository.findById(reply.getPost().getId());
          post.get().setComments(post.get().getComments() -1 );
          postRepository.save(post.get());
        }
      }
      commentRepository.delete(comment);
      Optional<Post> post = postRepository.findById(comment.getPost().getId());
      post.get().setComments(post.get().getComments() -1 );
      postRepository.save(post.get());
    }

    for(Post post: posts){
      postRepository.delete(post);
    }

    userRepository.delete(user.get());

    return deleteProfileResponse.builder()
        .userId(userId)
        .fullName(user.get().getFullName())
        .build();
  }

}
