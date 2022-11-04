package com.example.mungmatebackend.domain.user.controller;

import com.example.mungmatebackend.domain.user.dto.request.UserReq;
import com.example.mungmatebackend.domain.user.dto.response.UserRes;
import com.example.mungmatebackend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

  private PasswordEncoder passwordEncoder;
  private final UserService userService;

  @Operation(summary = "User 로그인 API")
  @PostMapping("/login")
  public UserRes postUser(@RequestBody UserReq user) {

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userService.login(user);

  }

//  @Operation(summary = "User 회원가입 API")
//  @PostMapping("/signup")
//  public UserRes
}
