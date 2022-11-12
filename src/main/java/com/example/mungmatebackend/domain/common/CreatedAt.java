package com.example.mungmatebackend.domain.common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CreatedAt {

  protected String getCreatedAt(LocalDateTime pastTime){
    String createdAt;
    LocalDateTime currentTime = LocalDateTime.now();

    if (ChronoUnit.YEARS.between(pastTime, currentTime) >= 1) {
      int year = (int) ChronoUnit.YEARS.between(pastTime, currentTime);
      createdAt = year + "년 전";
    }else if(ChronoUnit.MONTHS.between(pastTime, currentTime) >= 1){
      int month = (int) ChronoUnit.MONTHS.between(pastTime, currentTime);
      createdAt = month + "달 전";
    }else if(ChronoUnit.DAYS.between(pastTime, currentTime) >= 1){
      int day = (int) ChronoUnit.DAYS.between(pastTime, currentTime);
      createdAt = day + "일 전";
    }else if(ChronoUnit.HOURS.between(pastTime, currentTime) >= 1){
      int hour = (int) ChronoUnit.HOURS.between(pastTime, currentTime);
      createdAt = hour + "시간 전";
    }else if(ChronoUnit.MINUTES.between(pastTime, currentTime) >= 1){
      int minute = (int) ChronoUnit.MINUTES.between(pastTime, currentTime);
      createdAt = minute + "분 전";
    }else{
      createdAt = "방금 전";
    }
    return createdAt;
  }

}
