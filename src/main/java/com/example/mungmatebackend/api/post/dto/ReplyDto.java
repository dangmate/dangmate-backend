package com.example.mungmatebackend.api.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class ReplyDto {

  @Builder
  @Data
  public static class ReplyPostRequest{
    @Schema(example = "1", description = "유저 id")
    private Long userId;
    @Schema(example = "1", description = "댓글 id")
    private Long commentId;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

  @Builder
  @Data
  public static class ReplyPostResponse{
    @Schema(example = "200")
    private String statusCode;
    @Schema(example = "1")
    private Long postId;
    @Schema(example = "1")
    private Long userId;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

  @Builder
  @Data
  public static class ReplyPutRequest{
    @Schema(example = "1", description = "유저 id")
    private Long userId;
    @Schema(example = "1", description = "댓글 id")
    private Long commentId;
    @Schema(example = "1", description = "답글(대댓글) id")
    private Long replyId;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

  @Builder
  @Data
  public static class ReplyPutResponse{
    @Schema(example = "1", description = "유저 id")
    private Long userId;
    @Schema(example = "1", description = "답글(대댓글) id")
    private Long replyId;
    @Schema(example = "OO동에 이사온지 얼마 되지 않아 아는 친구가 한 명도 없어요ㅜㅜ나이는 25살 여자입니다!")
    private String content;
  }

}
