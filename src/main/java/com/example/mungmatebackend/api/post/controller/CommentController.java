package com.example.mungmatebackend.api.post.controller;

import com.example.mungmatebackend.api.post.dto.CommentDto;
import com.example.mungmatebackend.api.user.login.dto.response.UserLoginRes;
import com.example.mungmatebackend.domain.comment.service.CommentService;
import com.example.mungmatebackend.global.error.dto.ErrorRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
@Tag(name = "Post", description = "각 게시글 API")
public class CommentController {

  private final CommentService commentService;

  @Operation(summary = "게시글 댓글 작성 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "댓글 달기 성공",
          content = @Content(schema = @Schema(implementation = CommentDto.CommentPostResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "해당 게시글 또는 유저 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "댓글 달기 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @PostMapping("/{id}/comment")
  public ResponseEntity<CommentDto.CommentPostResponse> postComment(
      @Parameter(example = "1", description = "게시글 id")
      @PathVariable Long id,
      @RequestBody CommentDto.CommentPostRequest request){
    return ResponseEntity.ok(commentService.postComment(id, request));
  }

  @Operation(summary = "게시글 댓글 수정 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "댓글 수정 성공",
          content = @Content(schema = @Schema(implementation = CommentDto.CommentPostResponse.class))
      ),
      @ApiResponse(
          responseCode = "200",
          description = "해당 게시글 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "401",
          description = "해당 댓글을 소유한 유저가 아님",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "해당 댓글 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "댓글 수정 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @PutMapping("/{postId}/comment/{commentId}")
  public ResponseEntity<CommentDto.CommentPutResponse> putComment(
      @Parameter(name="postId", example = "1", description = "게시글 id")
      @PathVariable Long postId,
      @Parameter(name="commentId", example = "1", description = "댓글 id")
      @PathVariable Long commentId,
      @RequestBody CommentDto.CommentPutRequest request){
    return ResponseEntity.ok(commentService.putComment(postId, commentId, request));
  }

}
