package com.example.mungmatebackend.api.gallery.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.mungmatebackend.api.gallery.dto.response.GalleryRes;
import com.example.mungmatebackend.global.error.ErrorCode;
import com.example.mungmatebackend.global.error.exception.BusinessException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class GalleryController {

  private final AmazonS3Client amazonS3Client;

  private String S3Bucket = "mungmate-bucket";

  @PostMapping("")
  public ResponseEntity<GalleryRes> upload(MultipartFile multipartFile) {

    String originalName = multipartFile.getOriginalFilename(); // 파일 이름
    long size = multipartFile.getSize(); // 파일 크기

    ObjectMetadata objectMetaData = new ObjectMetadata();
    objectMetaData.setContentType(multipartFile.getContentType());
    objectMetaData.setContentLength(size);

    // S3에 업로드
    try {
      amazonS3Client.putObject(
          new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(),
              objectMetaData)
              .withCannedAcl(CannedAccessControlList.PublicRead)
      );
    } catch (IOException e) {
      throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXIST);
    }

    String imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString(); // 접근가능한 URL 가져오기

    return ResponseEntity.ok(GalleryRes.builder().imagePath(imagePath).build());
  }

}
