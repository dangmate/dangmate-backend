package com.example.mungmatebackend.api.user.login.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRes {

  @Schema(example = "200")
  private String statusCode;
  @Schema(example = "abcd@gmail.com")
  private String email;
  @Schema(example = "귀여운 댕댕이")
  private String fullName;

}
