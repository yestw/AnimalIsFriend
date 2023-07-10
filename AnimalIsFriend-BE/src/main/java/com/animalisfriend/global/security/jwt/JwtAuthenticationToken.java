package com.animalisfriend.global.security.jwt;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private String credentials;


	public JwtAuthenticationToken(
		Collection<? extends GrantedAuthority> authorities, Object principal, String credentials) {
		super(authorities);
		super.setAuthenticated(true);
		this.principal = principal;
		this.credentials = credentials;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException("이미 인증이 완료된 객체입니다.");
		}
		super.setAuthenticated(false);
	}
	@Override
	public Object getPrincipal() {
		return principal;
	}
}
