package com.example.mungmatebackend.api.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeDto {

  @Data
  @Builder
  public static class LikeRequestDto{
    @Schema(example = "1", description = "유저 Id")
    private Long userId;

    @Schema(example = "1", description = "게시글(post) Id")
    private Long postId;
  }

  @Data
  @Builder
  public static class LikeResponseDto{
    @Schema(example = "200")
    private String statusCode;

    @Schema(example = "1", description = "유저 Id")
    private Long userId;

    @Schema(example = "1", description = "게시글(post) Id")
    private Long postId;
  }

}
