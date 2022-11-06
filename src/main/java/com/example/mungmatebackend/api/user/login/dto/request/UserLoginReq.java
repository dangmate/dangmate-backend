package com.example.mungmatebackend.api.user.login.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginReq {
  @NotBlank(message = "해당 값은 필수 입력 값입니다.")
  @Schema(example = "abcd@gmail.com")
  private String email;
  @NotBlank(message = "해당 값은 필수 입력 값입니다.")
  @Schema(example = "1234")
  private String password;
}
