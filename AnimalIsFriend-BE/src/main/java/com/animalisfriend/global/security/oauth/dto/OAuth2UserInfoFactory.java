package com.animalisfriend.global.security.oauth.dto;

import java.util.Map;

import com.animalisfriend.domain.users.entity.Provider;
import com.animalisfriend.global.security.oauth.exception.ProviderNotFoundException;

public class OAuth2UserInfoFactory {

	public static OAuth2UserInfo getOAuth2UserInfo(Provider provider, Map<String, Object> attributes) {
		switch (provider) {
			case GOOGLE: return new GoogleOAuth2User(attributes);
			case KAKAO: return new KakaoOAuth2User(attributes);

			default: throw new ProviderNotFoundException();
		}
	}
}
