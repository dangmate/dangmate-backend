package com.example.mungmatebackend.domain.user.service;

import com.example.mungmatebackend.domain.user.dto.request.UserReq;
import com.example.mungmatebackend.domain.user.dto.response.UserRes;
import com.example.mungmatebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserRes login(UserReq user){
    UserRes userRes = userRepository.findByEmail(user.getEmail());
    if(userRes == null){
      return UserRes.builder()
          .statusCode("404")
          .build();
    }

    return UserRes.builder()
        .statusCode("200")
        .email(userRes.getEmail())
        .fullName(userRes.getFullName())
        .build();
  }

}
