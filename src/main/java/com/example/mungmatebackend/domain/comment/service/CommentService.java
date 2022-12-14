package com.example.mungmatebackend.domain.comment.service;

import com.example.mungmatebackend.api.post.dto.CommentsDto;
import com.example.mungmatebackend.api.post.dto.CommentDto;
import com.example.mungmatebackend.api.post.dto.CommentDto.CommentGetResponse;
import com.example.mungmatebackend.api.user.login.dto.UserDto;
import com.example.mungmatebackend.domain.comment.entity.Comment;
import com.example.mungmatebackend.domain.comment.repository.CommentRepository;
import com.example.mungmatebackend.domain.common.CreatedAt;
import com.example.mungmatebackend.domain.post.entity.Post;
import com.example.mungmatebackend.domain.post.repository.PostRepository;
import com.example.mungmatebackend.domain.user.entity.User;
import com.example.mungmatebackend.domain.user.repository.UserRepository;
import com.example.mungmatebackend.global.error.ErrorCode;
import com.example.mungmatebackend.global.error.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService extends CreatedAt {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public CommentDto.CommentPostResponse postComment(Long postId,
      CommentDto.CommentPostRequest request) {
    Optional<Post> post = postRepository.findById(postId);
    Optional<User> user = userRepository.findById(request.getUserId());

    if (post.isEmpty()) {
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }

    if (user.isEmpty()) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    commentRepository.save(
        Comment.builder()
            .content(request.getContent())
            .post(post.get())
            .user(user.get())
            .build()
    );

    post.get().setComments(post.get().getComments() + 1);
    postRepository.save(post.get());

    return CommentDto.CommentPostResponse.builder()
        .statusCode("200")
        .postId(post.get().getId())
        .userId(user.get().getId())
        .content(request.getContent())
        .build();
  }

  public CommentDto.CommentPutResponse putComment(
      Long postId,
      Long commentId,
      CommentDto.CommentPutRequest request
  ) {
    Optional<Comment> comment = commentRepository.findById(commentId);
    Optional<Post> post = postRepository.findById(postId);

    if (post.isEmpty()) {
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }

    if (comment.isEmpty()) {
      throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
    }

    if (!Objects.equals(comment.get().getUser().getId(), request.getUserId())) {
      throw new BusinessException(ErrorCode.COMMENT_USER_NOT_MATCH);
    }

    comment.get().setContent(request.getContent());
    commentRepository.save(comment.get());

    return CommentDto.CommentPutResponse.builder().build();
  }
  public UserDto.getMyCommentsResponse getMyComments(Long userId) {

    List<Comment> comments = commentRepository.findByUserIdOrderByIdDesc(userId);
    Optional<User> user = userRepository.findById(userId);

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    if (comments == null || comments.isEmpty()) {
      return UserDto.getMyCommentsResponse.builder()
              .build();
    }

    List<UserDto.getMyCommentResponse> myComments = new ArrayList<>();

    for (Comment comment : comments) {
      String createdAt = getCreatedAt(comment.getCreatedAt());

      myComments.add(UserDto.getMyCommentResponse.builder()
              .commentId(comment.getId())
              .profile(user.get().getProfile())
              .fullName(user.get().getFullName())
              .createdAt(createdAt)
              .content(comment.getContent())
              .reply(comment.getReply())
              .build());
    }

    return UserDto.getMyCommentsResponse.builder()
            .comments(myComments)
            .build();
  }

  public CommentsDto.CommentsGetResponse getComments(Long postId, Long userId) {

    List<Comment> comments = commentRepository.findByPostId(postId);

    if (comments == null || comments.isEmpty()) {
      throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
    }

    List<CommentGetResponse> commentGetResponses = new ArrayList<>();

    for (Comment comment : comments) {
      Optional<User> user = userRepository.findById(comment.getUser().getId());

      String createdAt = getCreatedAt(comment.getCreatedAt());

      commentGetResponses.add(CommentGetResponse.builder()
          .commentId(comment.getId())
          .profile(user.get().getProfile())
          .fullName(user.get().getFullName())
          .createdAt(createdAt)
          .content(comment.getContent())
          .reply(comment.getReply())
          .isComment(Objects.equals(comment.getUser().getId(), userId))
          .isActive(comment.getIsActive())
          .build());
    }

    return CommentsDto.CommentsGetResponse.builder()
        .comments(commentGetResponses)
        .build();
  }

  public CommentDto.CommentDeleteResponse deleteComment(
      Long postId,
      Long commentId,
      CommentDto.CommentDeleteRequest request
  ) {
    Optional<Post> post = postRepository.findById(postId);
    Optional<Comment> comment = commentRepository.findById(commentId);

    if (post.isEmpty()) {
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }

    if (comment.isEmpty()) {
      throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
    }

    if (!Objects.equals(comment.get().getUser().getId(), request.getUserId())) {
      throw new BusinessException(ErrorCode.COMMENT_USER_NOT_MATCH);
    }

    if(comment.get().getReply() > 0){
      comment.get().setIsActive(false);
      commentRepository.save(comment.get());
    }else{
      commentRepository.deleteById(commentId);
      post.get().setComments(post.get().getComments() - 1);
      postRepository.save(post.get());
    }

    return CommentDto.CommentDeleteResponse.builder()
        .statusCode("200")
        .userId(request.getUserId())
        .commentId(commentId)
        .build();
  }

}
