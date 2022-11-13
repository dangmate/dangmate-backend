package com.example.mungmatebackend.domain.comment.service;

import com.example.mungmatebackend.api.post.dto.CommentsDto;
import com.example.mungmatebackend.api.post.dto.CommentDto;
import com.example.mungmatebackend.api.post.dto.CommentDto.CommentGetResponse;
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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService extends CreatedAt {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public CommentDto.CommentPostResponse postComment(Long id,
      CommentDto.CommentPostRequest request) {
    Optional<Post> post = postRepository.findById(id);
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

  public CommentsDto.CommentsGetResponse getComments(Long postId) {

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
          .fullName(user.get().getFullName())
          .createdAt(createdAt)
          .content(comment.getContent())
          .reply(comment.getReply())
          .build());
    }

    return CommentsDto.CommentsGetResponse.builder()
        .comments(commentGetResponses)
        .build();
  }

}
