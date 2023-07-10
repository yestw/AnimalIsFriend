package com.animalisfriend.global.security.oauth.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.animalisfriend.domain.Users.entity.Provider;
import com.animalisfriend.domain.Users.entity.UserRole;
import com.animalisfriend.domain.Users.entity.Users;
import com.animalisfriend.domain.Users.exception.UserNotFoundException;
import com.animalisfriend.domain.Users.repository.UserRepository;
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
		log.info("--------------loadUser------------- 실행");
		OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

		//OAuth2 로그인 플랫폼 구분
		String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
		Provider provider = getProvider(registrationId);

		// log.info("oauth2user : {}", oAuth2User.getAttributes());

		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());

		// log.info("oauth2userInfo : {}", oAuth2UserInfo.getName());
		// log.info("oauth2userInfo : {}", oAuth2UserInfo.getOAuth2Id());
		Users users = userRepository.findByProviderId(oAuth2UserInfo.getOAuth2Id())
			.orElseGet(() -> registerUser(provider, oAuth2UserInfo));

		return new CustomOAuth2User(
			users, oAuth2UserInfo.getAttributes()
		);
	}


	private Provider getProvider(String registrationId) {
		log.info("--------------getProvider------------- 실행");
		log.info("registrationId : {}", registrationId);
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
		log.info("--------------registerUser------------- 실행");

		Users user = Users.builder()
			.userNickname(oAuth2UserInfo.getName())
			.providerId(oAuth2UserInfo.getOAuth2Id())
			.provider(provider)
			.role(UserRole.GUEST)
			.build();

		return userRepository.save(user);
	}
}
