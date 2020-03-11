package com.gabia.gyebalja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

/**
 * Author : 정태균
 * Part : getRestTemplate()
 */

@EnableJpaAuditing  // JPA Auditing 활성화
@SpringBootApplication
public class GyebaljaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GyebaljaApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
