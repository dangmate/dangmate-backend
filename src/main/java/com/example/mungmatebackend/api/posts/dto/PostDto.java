package com.example.mungmatebackend.api.posts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {

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

}
