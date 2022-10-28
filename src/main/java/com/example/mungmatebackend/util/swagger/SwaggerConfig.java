package com.example.mungmatebackend.util.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "mungmate API 명세서",
        description = "mungmate API 명세서 입니다.",
        version = "v0.0.1"
    )
)

@Configuration
public class SwaggerConfig {


}
