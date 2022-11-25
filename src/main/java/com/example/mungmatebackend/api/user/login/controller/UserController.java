package com.example.mungmatebackend.api.user.login.controller;

import com.example.mungmatebackend.api.post.dto.CommentsDto;
import com.example.mungmatebackend.api.user.login.dto.UserDto;
import com.example.mungmatebackend.domain.comment.service.CommentService;
import com.example.mungmatebackend.domain.post.service.PostService;
import com.example.mungmatebackend.domain.user.service.UserService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")
public class UserController {

  private final PostService postService;
  private final CommentService commentService;
  private final UserService userService;

  @Operation(summary = "내가 쓴 게시글 조회 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "게시글 조회 성공",
          content = @Content(schema = @Schema(implementation = UserDto.getMyPostsResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "존재 하지 않는 유저",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "조회 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @GetMapping("/{userId}/posts")
  public ResponseEntity<UserDto.getMyPostsResponse> getMyPosts(
      @Parameter(name = "userId", description = "유저 id")
      @PathVariable Long userId) {
    return ResponseEntity.ok(postService.getMyPosts(userId));
  }


  @Operation(summary = "내가 쓴 댓글 조회 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "댓글 조회 성공",
          content = @Content(schema = @Schema(implementation = UserDto.getMyCommentsResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "존재 하지 않는 유저",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "댓글 조회 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
  })
  @GetMapping("/{userId}/comments")
  public ResponseEntity<UserDto.getMyCommentsResponse> getMyComments(
      @Parameter(name = "userId", description = "유저 id")
      @PathVariable Long userId
  ) {
    return ResponseEntity.ok(commentService.getMyComments(userId));
  }

  @Operation(summary = "관심 목록 조회 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "관심 목록 조회 성공",
          content = @Content(schema = @Schema(implementation = UserDto.getMyCommentsResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "존재 하지 않는 유저",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "관심 목록 조회 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
  })
  @GetMapping("/{userId}/likes")
  public ResponseEntity<UserDto.getMyLikesResponse> getMyLikes(
      @Parameter(name = "userId", description = "유저 id")
      @PathVariable Long userId,
      @Parameter(name = "category", description = "'all', '산책 메이트', '댕댕 이야기' 중 1개")
      @RequestParam String category
  ) {
    return ResponseEntity.ok(postService.getMyLikes(userId, category));
  }

  @Operation(summary = "유저 프로필 조회 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "유저 조회 성공",
          content = @Content(schema = @Schema(implementation = UserDto.getProfileResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "존재 하지 않는 유저",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "유저 조회 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
  })
  @GetMapping("/{userId}")
  public ResponseEntity<UserDto.getProfileResponse> getProfile(
      @Parameter(name = "userId", description = "유저 id")
      @PathVariable Long userId
  ) {
    return ResponseEntity.ok(userService.getProfile(userId));
  }

  @Operation(summary = "유저 프로필 업데이트 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "유저 업데이트 성공",
          content = @Content(schema = @Schema(implementation = UserDto.updateProfileResponse.class))
      ),
      @ApiResponse(
          responseCode = "401",
          description = "7일 이내에 수정한 경우",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "존재 하지 않는 유저",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "유저 업데이트 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
  })
  @PutMapping("/{userId}")
  public ResponseEntity<UserDto.updateProfileResponse> updateProfile(
      @Parameter(name = "userId", description = "유저 id")
      @PathVariable Long userId,
      @RequestBody UserDto.updateProfileRequest request
  ) {
    return ResponseEntity.ok(userService.updateProfile(userId, request));
  }

  @Operation(summary = "유저 삭제 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "유저 삭제 성공",
          content = @Content(schema = @Schema(implementation = UserDto.deleteProfileResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "존재 하지 않는 유저",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "유저 업데이트 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      ),
  })
  @DeleteMapping("/{userId}")
  public ResponseEntity<UserDto.deleteProfileResponse> deleteProfile(
      @Parameter(name = "userId", description = "유저 id")
      @PathVariable Long userId
  ) {
    return ResponseEntity.ok(userService.deleteProfile(userId));
  }

}
