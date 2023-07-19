package com.animalisfriend.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.animalisfriend.global.common.auth.AuthInterceptor;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${server.frontEnd}")
	private String frontEndServer;
	@Value("${server.backEnd}")
	private String backEndServer;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins(backEndServer, frontEndServer, "/**", "http://localhost:8080", "http://localhost:3000")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
			.allowedHeaders("*")
			.allowCredentials(true);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor())
			.excludePathPatterns("/login/**", "/oauth2/**") // 인터셉터 실행 X
			.addPathPatterns("/**"); // 인터셉터 실행 O
	}
}
