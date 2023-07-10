package com.animalisfriend.global.security.jwt.dto;

import com.animalisfriend.global.security.jwt.entity.RefreshToken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenSaveRequestDto {

	private Long userId;
	private String refreshToken;

	@Builder
	public RefreshTokenSaveRequestDto(Long userId, String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}
	public static RefreshToken of(RefreshTokenSaveRequestDto dto) {
		return RefreshToken.builder()
			.refreshToken(dto.refreshToken)
			.userId(dto.userId)
			.build();
	}
}
