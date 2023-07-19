package com.animalisfriend.global.security.oauth.dto;

import java.util.Map;

public class KakaoOAuth2User extends OAuth2UserInfo{

	private Long id;

	public KakaoOAuth2User(Map<String, Object> attributes) {
		super((Map<String, Object>) attributes.get("kakao_account"));
		this.id = (Long) attributes.get("id");
	}

	@Override
	public String getOAuth2Id() {
		return this.id.toString();
	}

	@Override
	public String getName() {
		return (String) ((Map<String, Object>) attributes.get("profile")).get("nickname");
	}
}
