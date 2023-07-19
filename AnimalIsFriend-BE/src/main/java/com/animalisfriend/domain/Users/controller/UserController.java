package com.animalisfriend.domain.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalisfriend.domain.users.dto.UserSignupRequestDto;
import com.animalisfriend.domain.users.service.UserService;
import com.animalisfriend.global.Error.code.SuccessCode;
import com.animalisfriend.global.security.jwt.dto.JwtAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@AuthenticationPrincipal JwtAuthentication user,
		@RequestBody UserSignupRequestDto dto) {

		userService.signup(dto, user.userId);
		return ResponseEntity.ok().body(SuccessCode.SIGNUP_SUCCESS);
	}
}
