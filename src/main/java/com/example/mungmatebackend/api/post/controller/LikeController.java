package com.example.mungmatebackend.api.post.controller;

import com.example.mungmatebackend.api.post.dto.LikeDto;
import com.example.mungmatebackend.api.post.dto.LikeDto.LikeResponseDto;
import com.example.mungmatebackend.domain.likeUser.service.LikeUserService;
import com.example.mungmatebackend.global.error.dto.ErrorRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "Post", description = "각 게시글 API")
public class LikeController {

  private final LikeUserService likeUserService;

  @Operation(summary = "게시글 좋아요 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "좋아요 성공",
          content = @Content(schema = @Schema(implementation = LikeDto.LikeResponseDto.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "게시글(포스트) 또는 유저 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "업로드 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @PostMapping("/like")
  public ResponseEntity<LikeResponseDto> postLike(@RequestBody LikeDto.LikeRequestDto request) {
    return ResponseEntity.ok(likeUserService.like(request));
  }

  @Operation(summary = "게시글 좋아요 취소 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "좋아요 취소 성공",
          content = @Content(schema = @Schema(implementation = LikeDto.LikeResponseDto.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "게시글(포스트) 또는 유저 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "업로드 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @DeleteMapping("/unlike")
  public ResponseEntity<LikeResponseDto> postUnlike(@RequestBody LikeDto.LikeRequestDto request) {
    return ResponseEntity.ok(likeUserService.unlike(request));
  }
}