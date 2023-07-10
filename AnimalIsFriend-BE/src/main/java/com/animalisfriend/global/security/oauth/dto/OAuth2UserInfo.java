package com.animalisfriend.global.security.oauth.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

	protected Map<String, Object> attributes;

	public abstract String getOAuth2Id();
	public abstract String getName();
}
