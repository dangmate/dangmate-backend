package com.example.mungmatebackend.api.posts.dto;

import com.example.mungmatebackend.api.post.dto.PostDto.PostGetResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class PostsDto {

  @Builder
  @Data
  public static class GetPostsRequest{
    @NotBlank(message = "해당 값은 필수 입력 값입니다.")
    @Schema(example = "공덕동")
    private String location;

    @NotBlank(message = "해당 값은 필수 입력 값입니다.")
    @Schema(example = "산책 메이트", description = "'all', '산책 메이트', '댕댕 이야기' 중 1개")
    private String category;

    @NotBlank(message = "해당 값은 필수 입력 값입니다.")
    @Schema(example = "1", description = "유저 고유 Id 값")
    private Long userId;
  }

  @Builder
  @Data
  public static class GetPostsResponse{
    @Schema(example = "공덕동")
    private String location;
    private List<PostGetResponse> posts;
    @Schema(example = "34", description = "총 게시글 갯수")
    private Integer total;
    @Schema(example = "1", description = "첫 게시글 id")
    private Long firstId;
  }

}
