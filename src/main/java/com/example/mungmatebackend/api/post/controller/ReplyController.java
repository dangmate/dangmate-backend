package com.example.mungmatebackend.api.post.controller;

import com.example.mungmatebackend.api.post.dto.CommentsDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "Post", description = "각 게시글 API")
public class ReplyController {

  private final ReplyService replyService;

  @Operation(summary = "답글(대댓글) 작성 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "답글 작성 성공",
          content = @Content(schema = @Schema(implementation = ReplyDto.ReplyPostResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "해당 포스트 또는 상위 댓글 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "답글 작성 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
  })
  @PostMapping("/{id}/reply")
  public ResponseEntity<ReplyDto.ReplyPostResponse> postReply(
      @Parameter(name = "id", description = "게시글 id")
      @PathVariable Long id,
      @RequestBody ReplyDto.ReplyPostRequest request
  ){
    return ResponseEntity.ok(replyService.postReply(id, request));
  }

}
