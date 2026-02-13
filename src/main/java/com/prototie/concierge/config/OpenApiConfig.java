package com.prototie.concierge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Concierge API")
				.description("Concierge 로컬 테스트 프로젝트 REST API")
				.version("0.0.1-SNAPSHOT"));
	}
}
