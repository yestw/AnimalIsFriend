package com.animalisfriend.global.common.auth;

import java.io.IOException;
import java.security.Permission;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		//
		if( !(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		AuthRequired authRequired = handlerMethod.getMethodAnnotation(AuthRequired.class);

		if(Objects.isNull(authRequired)) {
			return true;
		}

		// String requestURI = request.getRequestURI();
		//
		// log.info("requestURI : {}" , requestURI);

		String role = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities());


		if (!(role.equals("[USER]") || role.equals("[ADMIN]"))) {
			ObjectMapper objectMapper = new ObjectMapper();
			response.setStatus(403);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter()
					.write(objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.FORBIDDEN_ERROR)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		return true;
	}
}
