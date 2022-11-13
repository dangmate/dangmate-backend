package com.example.mungmatebackend.api.post.controller;

import com.example.mungmatebackend.api.post.dto.CommentsDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "Post", description = "각 게시글 API")
public class CommentsController {

  private final CommentService commentService;

  @Operation(summary = "게시글 댓글 조회 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "댓글 조회 성공",
          content = @Content(schema = @Schema(implementation = CommentsDto.CommentsGetResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "댓글 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "댓글 조회 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
  })
  @GetMapping("/{postId}/comments")
  public ResponseEntity<CommentsDto.CommentsGetResponse> getComments(
      @Parameter(name = "postId", description = "게시글 id")
      @PathVariable Long postId
  ){
    return ResponseEntity.ok(commentService.getComments(postId));
  }

}
