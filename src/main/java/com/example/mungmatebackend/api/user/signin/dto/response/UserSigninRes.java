package com.example.mungmatebackend.api.user.signin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSigninRes {
    @Schema(example = "200")
    private String statusCode;

    @Schema(example = "abcd@gmail.com")
    private String email;

    @Schema(example = "귀여운 댕댕이")
    private String fullName;

    @Schema(example = "공덕동")
    private String location;
}
