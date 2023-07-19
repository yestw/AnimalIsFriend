package com.animalisfriend.domain.adopt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalisfriend.domain.adopt.dto.request.AdoptRequestDto;
import com.animalisfriend.domain.adopt.service.AdoptsService;
import com.animalisfriend.global.Error.code.SuccessCode;
import com.animalisfriend.global.common.auth.AuthRequired;
import com.animalisfriend.global.security.jwt.dto.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adopt")
public class AdoptsController {

	private final AdoptsService adoptsService;

	@PostMapping
	@AuthRequired
	public ResponseEntity<Object> adoptSuccess( @RequestBody AdoptRequestDto.updateAdopt dto,
		@AuthenticationPrincipal JwtAuthentication user) {

		adoptsService.adoptSuccess(dto, user.userId);

		return ResponseEntity.ok().body(SuccessCode.INSERT_SUCCESS);
	}
}
