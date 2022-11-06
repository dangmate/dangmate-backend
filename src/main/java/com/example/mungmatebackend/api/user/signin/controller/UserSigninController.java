package com.example.mungmatebackend.api.user.signin.controller;

import com.example.mungmatebackend.api.user.signin.dto.request.UserEmailReq;
import com.example.mungmatebackend.api.user.signin.dto.request.UserFullNameReq;
import com.example.mungmatebackend.api.user.signin.dto.request.UserSigninReq;
import com.example.mungmatebackend.api.user.signin.dto.response.UserEmailRes;
import com.example.mungmatebackend.api.user.signin.dto.response.UserFullNameRes;
import com.example.mungmatebackend.api.user.signin.dto.response.UserSigninRes;
import com.example.mungmatebackend.domain.user.service.UserService;
import com.example.mungmatebackend.global.error.dto.ErrorRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")
public class UserSigninController {

    private final UserService userService;

    @Operation(summary = "User 회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원 가입 성공",
                    content = @Content(schema = @Schema(implementation = UserSigninRes.class))
            )
    })
    @PostMapping("/signin")
    public ResponseEntity<UserSigninRes> signin(@Valid @RequestBody UserSigninReq userSigninReq){
        return ResponseEntity.ok(userService.signin(userSigninReq));
    }

    @Operation(summary = "User 이메일 중복 체크 API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "해당 이메일 가입 가능",
                    content = @Content(schema = @Schema(implementation = UserEmailRes.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "이메일 중복",
                    content = @Content(schema = @Schema(implementation = ErrorRes.class))
            )
    })
    @PostMapping("/email")
    public ResponseEntity<UserEmailRes> signinEmail(@Valid @RequestBody UserEmailReq userEmailReq){
        return ResponseEntity.ok(userService.signinEmail(userEmailReq));
    }

    @Operation(summary = "닉네임 중복 체크 API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "해당 닉네임 가입 가능",
                    content = @Content(schema = @Schema(implementation = UserFullNameRes.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "닉네임 중복",
                    content = @Content(schema = @Schema(implementation = ErrorRes.class))
            )
    })
    @PostMapping("/full-name")
    public ResponseEntity<UserFullNameRes> signinFullName(@Valid @RequestBody UserFullNameReq userFullNameReq){
        return ResponseEntity.ok(userService.signinFullName(userFullNameReq));
    }
}
