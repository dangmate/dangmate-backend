package com.example.mungmatebackend.domain.user.repository;

import com.example.mungmatebackend.domain.user.dto.response.UserRes;
import com.example.mungmatebackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserRes findByEmail(String email);
}
