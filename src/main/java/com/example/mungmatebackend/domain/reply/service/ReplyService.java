package com.example.mungmatebackend.domain.reply.service;

import com.example.mungmatebackend.api.post.dto.RepliesDto;
import com.example.mungmatebackend.api.post.dto.RepliesDto.RepliesGetResponse;
import com.example.mungmatebackend.api.post.dto.ReplyDto;
import com.example.mungmatebackend.api.post.dto.ReplyDto.ReplyGetResponse;
import com.example.mungmatebackend.domain.comment.entity.Comment;
import com.example.mungmatebackend.domain.comment.repository.CommentRepository;
import com.example.mungmatebackend.domain.common.CreatedAt;
import com.example.mungmatebackend.domain.post.entity.Post;
import com.example.mungmatebackend.domain.post.repository.PostRepository;
import com.example.mungmatebackend.domain.reply.entity.Reply;
import com.example.mungmatebackend.domain.reply.repository.ReplyRepository;
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
public class ReplyService extends CreatedAt {
  private final ReplyRepository replyRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  public RepliesDto.RepliesGetResponse getReplies(
      Long userId,
      Long postId,
      Long commentId
  ){

    Optional<Post> post = postRepository.findById(postId);
    List<Reply> replies = replyRepository.findByCommentId(commentId);

    if(post.isEmpty()){
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }

    if(replies == null || replies.isEmpty()){
      throw new BusinessException(ErrorCode.REPLY_NOT_FOUND);
    }

    List<ReplyDto.ReplyGetResponse> replyGetResponses = new ArrayList<>();
    for(Reply reply: replies){
      replyGetResponses.add(ReplyGetResponse.builder()
          .replyId(reply.getId())
          .profile(reply.getUser().getProfile())
          .fullName(reply.getUser().getFullName())
          .createdAt(getCreatedAt(reply.getCreatedAt()))
          .isReply(Objects.equals(reply.getUser().getId(), userId))
          .content(reply.getContent())
          .build());
    }

    return RepliesGetResponse.builder()
        .replies(replyGetResponses)
        .build();
  }

  public ReplyDto.ReplyPostResponse postReply(Long postId, ReplyDto.ReplyPostRequest request){

    Optional<Post> post = postRepository.findById(postId);
    Optional<Comment> comment = commentRepository.findById(request.getCommentId());
    Optional<User> user = userRepository.findById(request.getUserId());

    if(post.isEmpty()){
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }

    if(comment.isEmpty()){
      throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
    }

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    replyRepository.save(
        Reply.builder()
            .comment(comment.get())
            .user(user.get())
            .post(post.get())
            .content(request.getContent())
            .build()
    );

    post.get().setComments(post.get().getComments() + 1);
    postRepository.save(post.get());

    comment.get().setReply(comment.get().getReply() + 1);
    commentRepository.save(comment.get());

    return ReplyDto.ReplyPostResponse.builder()
        .statusCode("200")
        .postId(postId)
        .userId(request.getUserId())
        .content(request.getContent())
        .build();
  }

  public ReplyDto.ReplyPutResponse putReply(Long postId, Long replyId, ReplyDto.ReplyPutRequest request){
    Optional<Post> post = postRepository.findById(postId);
    Optional<Comment> comment = commentRepository.findById(request.getCommentId());
    Optional<Reply> reply = replyRepository.findById(replyId);
    Optional<User> user = userRepository.findById(request.getUserId());

    if(post.isEmpty()){
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }

    if(comment.isEmpty()){
      throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
    }

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    if(!Objects.equals(user.get().getId(),reply.get().getUser().getId())){
      throw new BusinessException(ErrorCode.REPLY_USER_NOT_MATCH);
    }

    reply.get().setContent(request.getContent());
    replyRepository.save(reply.get());

    return ReplyDto.ReplyPutResponse.builder()
        .statusCode("200")
        .userId(request.getUserId())
        .replyId(replyId)
        .content(request.getContent())
        .build();
  }

  public ReplyDto.ReplyDeleteResponse deleteReply(Long postId, Long replyId, ReplyDto.ReplyDeleteRequest request){
    Optional<Post> post = postRepository.findById(postId);
    Optional<Comment> comment = commentRepository.findById(request.getCommentId());
    Optional<Reply> reply = replyRepository.findById(replyId);
    Optional<User> user = userRepository.findById(request.getUserId());

    if(post.isEmpty()){
      throw new BusinessException(ErrorCode.POST_NOT_FOUND);
    }

    if(comment.isEmpty()){
      throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
    }

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    if(!Objects.equals(user.get().getId(),reply.get().getUser().getId())){
      throw new BusinessException(ErrorCode.REPLY_USER_NOT_MATCH);
    }

    post.get().setComments(post.get().getComments() - 1);
    postRepository.save(post.get());

    comment.get().setReply(comment.get().getReply() - 1);
    commentRepository.save(comment.get());

    replyRepository.deleteById(replyId);

    return ReplyDto.ReplyDeleteResponse.builder()
        .userId(request.getUserId())
        .replyId(replyId)
        .build();
  }

}
