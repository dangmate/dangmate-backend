package com.example.mungmatebackend.global.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorRes {
    @Schema(example = "해당 HTTP 상태 코드")
    private String errorCode;
    @Schema(example = "해당 에러 메세지")
    private String errorMessage;
}
