package com.animalisfriend.global.security.jwt;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		response.setStatus(UNAUTHORIZED.value());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter()
			.write(objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED)));
	}
}

