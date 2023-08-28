package com.animalisfriend.global.security.oauth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.animalisfriend.domain.users.entity.Provider;
import com.animalisfriend.domain.users.entity.UserRole;
import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.domain.users.exception.UserNotFoundException;
import com.animalisfriend.domain.users.repository.UserRepository;
import com.animalisfriend.global.security.oauth.dto.CustomOAuth2User;
import com.animalisfriend.global.security.oauth.dto.OAuth2UserInfo;
import com.animalisfriend.global.security.oauth.dto.OAuth2UserInfoFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

		//OAuth2 로그인 플랫폼 구분
		String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
		Provider provider = getProvider(registrationId);

		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());

		Users users = userRepository.findByProviderId(oAuth2UserInfo.getOAuth2Id())
			.orElseGet(() -> registerUser(provider, oAuth2UserInfo));

		return new CustomOAuth2User(
			users, oAuth2UserInfo.getAttributes()
		);
	}


	private Provider getProvider(String registrationId) {
		switch (registrationId) {
			case "google":
				return Provider.GOOGLE;
			case "kakao":
				return Provider.KAKAO;
			default:
				throw new UserNotFoundException();
		}
	}


	private Users registerUser(Provider provider, OAuth2UserInfo oAuth2UserInfo) {
		Users user = Users.builder()
			.userNickname(oAuth2UserInfo.getName())
			.providerId(oAuth2UserInfo.getOAuth2Id())
			.provider(provider)
			.role(UserRole.GUEST)
			.build();

		return userRepository.save(user);
	}
}
