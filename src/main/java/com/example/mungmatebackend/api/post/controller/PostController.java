package com.example.mungmatebackend.api.post.controller;

import com.example.mungmatebackend.api.post.dto.LikeDto;
import com.example.mungmatebackend.api.post.dto.PostDto;
import com.example.mungmatebackend.domain.post.service.PostService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "Post", description = "각 게시글 API")
public class PostController {

  private final PostService postService;

  @Operation(summary = "게시글 업로드 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "게시글(포스트) 업로드 성공",
          content = @Content(schema = @Schema(implementation = PostDto.PostUploadResponse.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "업로드 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @PostMapping("")
  public ResponseEntity<PostDto.PostUploadResponse> uploadPost(
      @RequestBody PostDto.PostUploadRequest request) {
    return ResponseEntity.ok(postService.uploadPost(request));
  }

  @Operation(summary = "게시글 조회 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "게시글 조회 성공",
          content = @Content(schema = @Schema(implementation = PostDto.PostGetResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "해당 게시글 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "조회 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @GetMapping("/{postId}/user/{userId}")
  public ResponseEntity<PostDto.PostGetResponse> getPost(
      @Parameter(name = "postId", description = "게시글 id")
      @PathVariable Long postId,
      @Parameter(name = "userId", description = "유저 id")
      @PathVariable Long userId) {
    return ResponseEntity.ok(postService.getPost(postId, userId));
  }

  @Operation(summary = "게시글 수정 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "게시글 수정 성공",
          content = @Content(schema = @Schema(implementation = PostDto.PostGetResponse.class))
      ),
      @ApiResponse(
          responseCode = "401",
          description = "해당 게시글을 소유한 유저가 아님",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "해당 게시글 없음",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "수정 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @PutMapping("/{postId}/user/{userId}")
  public ResponseEntity<PostDto.PostPutResponse> putPost(
      @Parameter(name = "postId", description = "게시글 id")
      @PathVariable Long postId,
      @Parameter(name = "userId", description = "유저 id")
      @PathVariable Long userId,
      @RequestBody PostDto.PostPutRequest request) {
    return ResponseEntity.ok(postService.putPost(postId, userId, request));
  }

}
