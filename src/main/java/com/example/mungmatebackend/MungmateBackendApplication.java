package com.example.mungmatebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
public class MungmateBackendApplication {
	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "local");
		SpringApplication.run(MungmateBackendApplication.class, args);
	}
}
