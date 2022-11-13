package com.example.mungmatebackend.api.post.dto;

import com.example.mungmatebackend.api.post.dto.CommentDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

public class CommentsDto {

  @Data
  @Builder
  public static class CommentsGetResponse{
    private List<CommentDto.CommentGetResponse> comments;
  }
}
