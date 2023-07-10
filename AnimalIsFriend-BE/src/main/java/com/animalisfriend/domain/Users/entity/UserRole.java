package com.animalisfriend.domain.Users.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
	GUEST("ROLE_GUEST"),
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String key;
}
