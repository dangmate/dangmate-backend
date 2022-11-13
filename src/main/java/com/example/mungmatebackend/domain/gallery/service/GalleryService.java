package com.example.mungmatebackend.domain.gallery.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.mungmatebackend.api.gallery.dto.GalleryDto;
import com.example.mungmatebackend.global.error.ErrorCode;
import com.example.mungmatebackend.global.error.exception.BusinessException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GalleryService {

  private final AmazonS3Client amazonS3Client;
  @Value("${cloud.aws.bucket}")
  private String S3Bucket;

  public GalleryDto.GalleryResponse uploadImage(GalleryDto.GalleryRequest request){
    MultipartFile multipartFile = request.getMultipartFile();

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

   return GalleryDto.GalleryResponse.builder().imagePath(imagePath).build();

  }

  public void deleteImage(String imagePath){
    boolean isExistObject = amazonS3Client.doesObjectExist(S3Bucket, imagePath);
    if (isExistObject == true) {
      amazonS3Client.deleteObject(S3Bucket, imagePath);
    }
  }

}
