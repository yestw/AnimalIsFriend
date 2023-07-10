package com.animalisfriend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;

import com.animalisfriend.global.security.jwt.ExceptionHandlerFilter;
import com.animalisfriend.global.security.jwt.JwtAuthenticationEntryPoint;
import com.animalisfriend.global.security.jwt.JwtAuthenticationFilter;
import com.animalisfriend.global.security.oauth.OAuth2AuthenticationFailureHandler;
import com.animalisfriend.global.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.animalisfriend.global.security.oauth.repository.CookieAuthorizationRequestRepository;
import com.animalisfriend.global.security.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ExceptionHandlerFilter exceptionHandlerFilter;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	@Bean
	public static CookieAuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
		return new CookieAuthorizationRequestRepository();
	}
	@Bean
	public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {

		//httpBasic은 쿠키와 세션을 이용한 방식이 아니라 request header에 id와 password값을 직접 날리는 방식이라 보안에 굉장히 취약하기에 해제
		//서버를 Stateless하게 유지합니다. 이걸 설정하면 Spring Security에서 세션을 만들지 않음
		http
			.csrf().disable()
			.cors()
			.and()
		//요청에 대한 권한 설정

			.authorizeHttpRequests()
			.antMatchers("/login/**", "/oauth2/**").permitAll()
			.antMatchers("/api/v1/user/**").hasAuthority("USER")
			.anyRequest().authenticated()
			.and()
			.httpBasic().disable()
			.formLogin().disable()
			.logout().disable()
			.headers().disable()
			.requestCache().disable()
			.rememberMe().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()


		//oauth2Login
			.oauth2Login()
			.authorizationEndpoint().baseUri("/oauth2/authorize")  // 소셜 로그인 url
			.authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())  // 인증 요청을 cookie 에 저장
			.and()
			.redirectionEndpoint().baseUri("/login/oauth2/code/**")  // 소셜 인증 후 redirect url
			.and()
			//userService()는 OAuth2 인증 과정에서 Authentication 생성에 필요한 OAuth2User 를 반환하는 클래스를 지정한다.
			.userInfoEndpoint().userService(customOAuth2UserService)  // 회원 정보 처리
			.and()
			.successHandler(oAuth2AuthenticationSuccessHandler)
			.failureHandler(oAuth2AuthenticationFailureHandler)
			.and()

			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()

		//jwt filter 설정
			.addFilterBefore(jwtAuthenticationFilter, OAuth2AuthorizationRequestRedirectFilter.class)
			.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);

		return http.build();
	}
}
