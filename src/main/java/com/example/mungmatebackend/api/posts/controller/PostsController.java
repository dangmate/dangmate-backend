package com.example.mungmatebackend.api.posts.controller;

import com.example.mungmatebackend.api.posts.dto.PostsDto;
import com.example.mungmatebackend.domain.post.service.PostService;
import com.example.mungmatebackend.global.error.dto.ErrorRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "게시글 리스트 API")
public class PostsController {

  private final PostService postService;

  @Operation(summary = "게시글 리스트 조회 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "리스트 조회 성공",
          content = @Content(schema = @Schema(implementation = PostsDto.GetPostsResponse.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "리스트 불러오기 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @PostMapping("")
  public ResponseEntity<PostsDto.GetPostsResponse> posts(
      @Parameter(name = "size", description = "받아올 리스트의 개수", example = "5")
      @RequestParam Long size,
      @Parameter(name = "lastPostId", description = "마지막으로 본 게시글 id", example = "10")
      @RequestParam(required = false) Long lastPostId,
      @Valid @RequestBody PostsDto.GetPostsRequest request
  ) {
    return ResponseEntity.ok(postService.getPosts(size, lastPostId, request));
  }

  @Operation(summary = "게시글 리스트 조회(구경하기) API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "리스트 조회 성공",
          content = @Content(schema = @Schema(implementation = PostsDto.GetPostsResponse.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "리스트 불러오기 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @GetMapping("")
  public ResponseEntity<PostsDto.GetPostsResponse> getAllPosts(
      @Parameter(name = "size", description = "받아올 리스트의 개수", example = "5")
      @RequestParam Long size,
      @Parameter(name = "lastPostId", description = "마지막으로 본 게시글 id", example = "10")
      @RequestParam(required = false) Long lastPostId,
      @Parameter(name = "category", description = "'all', '산책 메이트', '댕댕 이야기' 중 1개", example = "all")
      @RequestParam String category
  ) {
    return ResponseEntity.ok(postService.getAllPosts(size, lastPostId, category));
  }

}
