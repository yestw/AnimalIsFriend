package com.animalisfriend.global.security.jwt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refreshTokenId;

	private String refreshToken;
	private Long userId;

	@Builder
	public RefreshToken(String refreshToken, Long userId) {
		this.refreshToken = refreshToken;
		this.userId = userId;
	}

	public void updateToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
