package com.example.mungmatebackend.global.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "일치하는 이메일이 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "401", "비밀번호가 일치하지 않습니다.")
    ;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
