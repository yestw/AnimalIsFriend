package com.animalisfriend.global.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.animalisfriend.global.security.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("JwtAuthenticationFilter - doFilter");

		String accessToken = jwtService.getAccessToken(request).orElse(null);
		//2. validateToken 메서드로 토큰 유효성 검사
		log.info("doFilter - accessToken : {}", accessToken);
		if (accessToken != null) {
			JwtAuthenticationToken authentication = jwtTokenProvider.getAuthentication(accessToken);
			log.info("doFilter - authentication 정보 : {}", authentication.getPrincipal());
			log.info("doFilter - authentication 정보 : {}", authentication.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
}
