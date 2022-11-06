package com.example.mungmatebackend.api.user.signin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEmailRes {
    @Schema(example = "200")
    private String statusCode;

    @Schema(example = "abcd@gmail.com")
    private String email;
}
