package com.example.mungmatebackend.api.gallery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
public class GalleryDto {

  @Data
  @Builder
  public static class request implements Serializable {
    @NotBlank(message = "해당 값은 필수 입력 값입니다.")
    @Schema(example = "Input 업로드 파일")
    private MultipartFile multipartFile;
  }

  @Data
  @Builder
  public static class response{
    @Schema(example = "https://mungmate-bucket.s3.ap-northeast-2.amazonaws.com/7tCZjBltnA")
    private String imagePath;
  }

}
