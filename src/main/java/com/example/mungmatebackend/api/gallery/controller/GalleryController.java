package com.example.mungmatebackend.api.gallery.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.mungmatebackend.api.gallery.dto.GalleryDto;
import com.example.mungmatebackend.api.user.login.dto.response.UserLoginRes;
import com.example.mungmatebackend.domain.gallery.service.GalleryService;
import com.example.mungmatebackend.global.error.ErrorCode;
import com.example.mungmatebackend.global.error.dto.ErrorRes;
import com.example.mungmatebackend.global.error.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File", description = "파일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gallery")
public class GalleryController {

  private final GalleryService galleryService;

  @Operation(summary = "파일 업로드 API")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "파일 업로드 성공",
          content = @Content(schema = @Schema(implementation = GalleryDto.GalleryResponse.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "파일 업로드 실패",
          content = @Content(schema = @Schema(implementation = ErrorRes.class))
      )
  })
  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GalleryDto.GalleryResponse> upload(
      @ModelAttribute
      @RequestPart("multipartFile")
      @Parameter(description = "multipart/form-data 형식을 body로 받습니다. key 값은 multipartFile 입니다.")
      GalleryDto.GalleryRequest request) {
    return ResponseEntity.ok(galleryService.uploadImage(request));
  }

}
