package com.example.mungmatebackend.domain.user.service;

import com.example.mungmatebackend.api.user.dto.request.UserReq;
import com.example.mungmatebackend.api.user.dto.response.UserRes;
import com.example.mungmatebackend.domain.user.entity.User;
import com.example.mungmatebackend.domain.user.repository.UserRepository;
import com.example.mungmatebackend.global.config.error.ErrorCode;
import com.example.mungmatebackend.global.config.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public UserRes login(UserReq userReq){
    User user = userRepository.findByEmail(userReq.getEmail());

    if(user == null){
      throw new BusinessException(ErrorCode.EMAIL_NOT_FOUND);
    }

    if(!passwordEncoder.matches(userReq.getPassword(), user.getPassword())){
      throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    return UserRes.builder()
        .statusCode("200")
        .email(user.getEmail())
        .fullName(user.getFullName())
        .build();
  }

}
