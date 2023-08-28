package com.animalisfriend.global.security.jwt.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animalisfriend.global.security.jwt.JwtTokenProvider;
import com.animalisfriend.global.security.jwt.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	public Optional<String> getAccessToken(HttpServletRequest request) {
		String accessToken = jwtTokenProvider.extractAccessToken(request)
			.orElse(null);

		if(accessToken != null) {
			jwtTokenProvider.validateToken(accessToken);
		}

		return Optional.ofNullable(accessToken);
	}

	@Transactional
	public void deleteRefreshToken(String refreshToken) {
		refreshTokenRepository.deleteByRefreshToken(refreshToken);
	}
}
