package com.animalisfriend.global.security.oauth;

import static com.animalisfriend.global.security.oauth.repository.CookieAuthorizationRequestRepository.*;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.animalisfriend.domain.users.entity.UserRole;
import com.animalisfriend.global.security.jwt.JwtTokenProvider;
import com.animalisfriend.global.security.oauth.dto.CustomOAuth2User;
import com.animalisfriend.global.security.oauth.repository.CookieAuthorizationRequestRepository;
import com.animalisfriend.global.util.CookieUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;
	private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

	@Value("${jwt.expire-seconds.access-token}")
	long accessTokenExpireSeconds;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		String targetUrl = determineTargetUrl(request, response, authentication);


		String accessToken = jwtTokenProvider.createAccessToken(
			oAuth2User.getUser()
		);

		setAccessTokenInCookie(response, accessToken);

		if(oAuth2User.getUser().getRole() == UserRole.GUEST) {
			targetUrl+="/signup";
		}

		response.sendRedirect(targetUrl);
	}

	private void setAccessTokenInCookie(HttpServletResponse response, String accessToken) {
		ResponseCookie token = ResponseCookie.from("accessTokenCookie", accessToken)
			.path(getDefaultTargetUrl())
			.sameSite("None")
			.secure(true)
			.maxAge(accessTokenExpireSeconds)
				.domain(".animalisfriend.shop")
			.build();

		response.addHeader("Set-Cookie", token.toString());
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue);
		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
		return targetUrl;
	}

	private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}
}
