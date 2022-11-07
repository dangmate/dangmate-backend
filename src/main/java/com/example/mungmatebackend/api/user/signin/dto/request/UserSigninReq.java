package com.example.mungmatebackend.api.user.signin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSigninReq {

    @NotBlank(message = "해당 값은 필수 입력 값 입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Schema(example = "abcd@gmail.com")
    private String email;

    @NotBlank(message = "해당 값은 필수 입력 값 입니다.")
    @Schema(example = "123456")
    private String password;

    @NotBlank(message = "해당 값은 필수 입력 값 입니다.")
    @Schema(example = "귀여운 댕댕이")
    private String fullName;

    @NotBlank(message = "해당 값은 필수 입력 값 입니다.")
    @Schema(example = "공덕동")
    private String location;

}
