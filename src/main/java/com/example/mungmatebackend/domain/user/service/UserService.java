package com.example.mungmatebackend.domain.user.service;

import com.example.mungmatebackend.api.user.login.dto.request.UserLoginReq;
import com.example.mungmatebackend.api.user.login.dto.response.UserLoginRes;
import com.example.mungmatebackend.api.user.signin.dto.request.UserEmailReq;
import com.example.mungmatebackend.api.user.signin.dto.request.UserFullNameReq;
import com.example.mungmatebackend.api.user.signin.dto.request.UserSigninReq;
import com.example.mungmatebackend.api.user.signin.dto.response.UserEmailRes;
import com.example.mungmatebackend.api.user.signin.dto.response.UserFullNameRes;
import com.example.mungmatebackend.api.user.signin.dto.response.UserSigninRes;
import com.example.mungmatebackend.domain.user.entity.User;
import com.example.mungmatebackend.domain.user.repository.UserRepository;
import com.example.mungmatebackend.global.config.error.ErrorCode;
import com.example.mungmatebackend.global.config.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public UserLoginRes login(UserLoginReq userLoginReq){
    Optional<User> user = userRepository.findByEmail(userLoginReq.getEmail());

    if(user.isEmpty()){
      throw new BusinessException(ErrorCode.EMAIL_NOT_FOUND);
    }

    if(!passwordEncoder.matches(userLoginReq.getPassword(), user.get().getPassword())){
      throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    return UserLoginRes.builder()
        .statusCode("200")
        .email(user.get().getEmail())
        .fullName(user.get().getFullName())
        .build();
  }

  public UserSigninRes signin(UserSigninReq userSigninReq){

    User newUser = User.builder()
            .email(userSigninReq.getEmail())
            .build();

    userRepository.save(newUser);

    return UserSigninRes.builder()
            .statusCode("200")
            .email(userSigninReq.getEmail())
            .fullName(userSigninReq.getFullName())
            .build();
  }

  public UserEmailRes signinEmail(UserEmailReq userEmailReq){
    Optional<User> user = userRepository.findByEmail(userEmailReq.getEmail());

    if(user.isPresent()){
      throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXIST);
    }

    return UserEmailRes.builder()
            .statusCode("200")
            .email(userEmailReq.getEmail())
            .build();
  }

  public UserFullNameRes signinFullName(UserFullNameReq userFullNameReq){
    Optional<User> user = userRepository.findByFullName(userFullNameReq.getFullName());

    if(user.isPresent()){
      throw new BusinessException(ErrorCode.FULL_NAME_ALREADY_EXIST);
    }

    return UserFullNameRes.builder()
            .statusCode("200")
            .fullName(userFullNameReq.getFullName())
            .build();
  }

}
