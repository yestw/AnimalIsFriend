package com.animalisfriend.global.security.oauth.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.animalisfriend.domain.users.entity.Users;

import lombok.Getter;

@Getter
public class CustomOAuth2User implements OAuth2User {

	private Users user;
	private Map<String, Object> attributes;

	public CustomOAuth2User(Users user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey()));
	}

	@Override
	public String getName() {
		return user.getUserNickname();
	}
}
