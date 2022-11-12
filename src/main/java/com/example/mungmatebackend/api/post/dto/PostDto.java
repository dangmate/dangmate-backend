package com.example.mungmatebackend.api.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {

  @Data
  @Builder
  public static class PostUploadRequest{
    @Schema(example = "1")
    private Long userId;

    @Schema(example = "공덕동")
    private String location;

    @Schema(example = "산책 메이트", description="'산책 메이트', '댕댕 이야기' 중 1개")
    private String category;

    @Schema(example = "https://mungmate-bucket.s3.ap-northeast-2.amazonaws.com/AbCoikG9xH", nullable = true)
    private String thumbnail;

    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

  @Data
  @Builder
  public static class PostUploadResponse{
    @Schema(example = "200")
    private String statusCode;
    @Schema(example = "3")
    private Long postId;
  }

  @Data
  @Builder
  public static class PostGetResponse{
    @Schema(example = "https://mungmate-bucket.s3.ap-northeast-2.amazonaws.com/AbCoikG9xH")
    private String profile;
    @Schema(example = "소심쟁이 제이")
    private String fullName;
    @Schema(example = "댕댕 이야기")
    private String category;
    @Schema(example = "https://mungmate-bucket.s3.ap-northeast-2.amazonaws.com/AbCoikG9xH")
    private String thumbnail;
    @Schema(example = "강아지 주인 찾아요.")
    private String content;
    @Schema(example = "공덕동")
    private String location;
    @Schema(example = "1분", description = "'n분 전', 'n시간 전', 'n일 전', 'n달 전', 'n년 전' 중 1개")
    private String createdAt;
    @Schema(example = "0", description = "댓글 갯수 / Integer")
    private Integer comments;
    @Schema(example = "0", description = "좋아요 갯수 / Integer")
    private Integer likes;
    @Schema(example = "true", description = "내가 좋아요 눌렀는지 / Boolean")
    private Boolean isLike;
    @Schema(example = "13", description = "조회수")
    private Integer views;
  }

}
