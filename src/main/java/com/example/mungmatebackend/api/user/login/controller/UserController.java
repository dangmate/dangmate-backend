package com.example.mungmatebackend.api.user.login.controller;

import com.example.mungmatebackend.api.post.dto.PostDto;
import com.example.mungmatebackend.api.user.login.dto.UserDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")
public class UserController {

    private final PostService postService;

    @Operation(summary = "내가 쓴 게시글 조회 API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserDto.getMyPostsResponse.class))
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
}
