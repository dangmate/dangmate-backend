package com.example.mungmatebackend.api.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

  @Data
  @Builder
  public static class CommentPostRequest {

    @Schema(example = "1", description = "유저 id")
    private Long userId;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

  @Data
  @Builder
  public static class CommentPostResponse {

    @Schema(example = "200")
    private String statusCode;
    @Schema(example = "1")
    private Long postId;
    @Schema(example = "1")
    private Long userId;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

  @Data
  @Builder
  public static class CommentPutRequest {

    @Schema(example = "1", description = "유저 id")
    private Long userId;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

  @Data
  @Builder
  public static class CommentPutResponse {

    @Schema(example = "200")
    private String statusCode;
    @Schema(example = "1")
    private Long postId;
    @Schema(example = "1")
    private Long commentId;
    @Schema(example = "1")
    private Long userId;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

  @Data
  @Builder
  public static class CommentGetResponse {

    @Schema(example = "1")
    private Long commentId;
    @Schema(example = "https://mungmate-bucket.s3.ap-northeast-2.amazonaws.com/AbCoikG9xH")
    private String profile;
    @Schema(example = "소심쟁이 제이")
    private String fullName;
    @Schema(example = "방금 전")
    private String createdAt;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
    @Schema(example = "0", description = "답글 갯수")
    private Integer reply;
    @Schema(example = "true", description = "댓글 작성 여부")
    private Boolean isComment;
  }

  @Data
  @Builder
  public static class CommentDeleteRequest {
    @Schema(example = "1", description = "유저 id")
    private Long userId;
  }

  @Data
  @Builder
  public static class CommentDeleteResponse {
    @Schema(example = "200")
    private String statusCode;
    @Schema(example = "1")
    private Long userId;
    @Schema(example = "1")
    private Long commentId;
  }
}
