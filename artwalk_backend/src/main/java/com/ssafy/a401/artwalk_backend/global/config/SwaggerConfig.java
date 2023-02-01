package com.ssafy.a401.artwalk_backend.global.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.service.contexts.SecurityContext;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Artwalk API")
			.description("Artwalk REST API Document")
			.version("1.0")
			.build();
	}

	private Set<String> consumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		return consumes;
	}

	private Set<String> produceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		produces.add("image/png");
		return produces;
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("accessToken", authorizationScopes));
	}

	private ApiKey apiKey() {
		return new ApiKey("accessToken", "accessToken", "header");
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
			.securityContexts(Arrays.asList(securityContext()))
			.securitySchemes(Arrays.asList(apiKey()))
			.consumes(consumeContentTypes())
			.produces(produceContentTypes())
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.ssafy.a401.artwalk_backend.domain"))
			.paths(PathSelectors.ant("/**"))
			.build();
	}
}
