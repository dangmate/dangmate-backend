package com.example.mungmatebackend.api.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GalleryDto {

  @Data
  @Builder
  public static class request{

  }

  @Data
  @Builder
  public static class response{
    private String imagePath;
  }

  private String imagePath;
}
