package com.example.mungmatebackend.api.post.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

public class RepliesDto {

  @Data
  @Builder
  public static class RepliesGetResponse{
    private List<ReplyDto.ReplyGetResponse> replies;
  }

}
