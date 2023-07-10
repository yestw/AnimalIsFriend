package com.animalisfriend.global.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			setErrorResponse(response, ErrorCode.TOKEN_EXPIRED);
		} catch (JwtException e) {
			logger.warn(e.getMessage());
			setErrorResponse(response, ErrorCode.TOKEN_INVALID);
		}

	}

	private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
		response.setStatus(errorCode.getStatus());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter()
				.write(objectMapper.writeValueAsString(ErrorResponse.of(errorCode)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
