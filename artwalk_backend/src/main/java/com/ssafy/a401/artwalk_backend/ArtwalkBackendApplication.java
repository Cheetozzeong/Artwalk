package com.ssafy.a401.artwalk_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
// @EnableJpaRepositories(basePackages = {"com.ssafy.a401.artwalk_backend.domain.*"})
// @EntityScan(basePackages = {"com.ssafy.a401.artwalk_backend.domain.*"})
public class ArtwalkBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtwalkBackendApplication.class, args);
	}

}