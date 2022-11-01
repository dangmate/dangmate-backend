package com.example.mungmatebackend.domain.user.controller;

import com.example.mungmatebackend.domain.user.dto.request.UserReq;
import com.example.mungmatebackend.domain.user.dto.response.UserRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User", description = "유저 API")
@RequestMapping("/api/user")
public class UserController {

  @Operation(summary = "User 로그인 API")
  @PostMapping("")
  public UserRes postUser(@RequestBody UserReq user) {
    return "true";
  }
}
