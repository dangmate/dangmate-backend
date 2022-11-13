package com.example.mungmatebackend.api.post.controller;

import com.example.mungmatebackend.api.post.dto.RepliesDto;
import com.example.mungmatebackend.api.post.dto.RepliesDto.RepliesGetResponse;
import com.example.mungmatebackend.api.post.dto.ReplyDto;
import com.example.mungmatebackend.domain.reply.service.ReplyService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "Post", description = "각 게시글 API")
public class RepliesController {

  private final ReplyService replyService;

  @Operation(summary = "게시글 답글(대댓글) 조회 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "답글 조회 성공",
          content = @Content(schema = @Schema(implementation = ReplyDto.ReplyGetResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "해당 포스트 없음 || 답글 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "답글 조회 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
  })
  @GetMapping("/{postId}/comment/{commentId}/replies")
  public ResponseEntity<RepliesDto.RepliesGetResponse> getReplies(
      @Parameter(name = "userId", description = "유저 id")
      @RequestParam Long userId,
      @Parameter(name = "postId", description = "게시글 id")
      @PathVariable Long postId,
      @Parameter(name = "commentId", description = "댓글 id")
      @PathVariable Long commentId
  ) {
    return ResponseEntity.ok(replyService.getReplies(userId, postId, commentId));
  }

}
