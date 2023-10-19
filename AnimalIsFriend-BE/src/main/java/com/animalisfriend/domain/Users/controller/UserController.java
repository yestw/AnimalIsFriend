package com.animalisfriend.domain.users.controller;

import com.animalisfriend.domain.users.dto.UserUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

	//회원탈퇴
	@DeleteMapping
	public ResponseEntity<Object> withdrawalMembership(@AuthenticationPrincipal JwtAuthentication user) {
		userService.withdrawalMembership(user.userId);

		return ResponseEntity.ok().body(SuccessCode.DELETE_SUCCESS);
	}
	
	//회원수정
	@PatchMapping
	public ResponseEntity<Object> updateUser(@AuthenticationPrincipal JwtAuthentication user,
											 @RequestBody UserUpdateRequestDto dto) {
		userService.updateUser(dto, user.userId);

		return ResponseEntity.ok().body(SuccessCode.UPDATE_SUCCESS);
	}
}
