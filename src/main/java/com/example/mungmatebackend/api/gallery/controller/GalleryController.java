package com.example.mungmatebackend.api.gallery.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.mungmatebackend.api.gallery.dto.GalleryDto;
import com.example.mungmatebackend.global.error.ErrorCode;
import com.example.mungmatebackend.global.error.exception.BusinessException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gallery")
public class GalleryController {

  private final AmazonS3Client amazonS3Client;

  private String S3Bucket = "mungmate-bucket";

  @PostMapping("")
  public ResponseEntity<GalleryDto.response> upload(MultipartFile multipartFile) {
    String randomGeneratedString = RandomStringUtils.randomAlphanumeric(10);
    long size = multipartFile.getSize();

    ObjectMetadata objectMetaData = new ObjectMetadata();
    objectMetaData.setContentType(multipartFile.getContentType());
    objectMetaData.setContentLength(size);

    try {
      amazonS3Client.putObject(
          new PutObjectRequest(S3Bucket, randomGeneratedString, multipartFile.getInputStream(),
              objectMetaData)
              .withCannedAcl(CannedAccessControlList.PublicRead)
      );
    } catch (IOException e) {
      throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXIST);
    }

    String imagePath = amazonS3Client.getUrl(S3Bucket, randomGeneratedString).toString();

    return ResponseEntity.ok(GalleryDto.response.builder().imagePath(imagePath).build());
  }

}
