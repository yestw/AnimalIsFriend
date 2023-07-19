package com.animalisfriend.global.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.global.security.jwt.dto.JwtAuthentication;
import com.animalisfriend.global.security.jwt.dto.RefreshTokenSaveRequestDto;
import com.animalisfriend.global.security.jwt.exception.TokenExpiredException;
import com.animalisfriend.global.security.jwt.exception.TokenInvalidException;
import com.animalisfriend.global.security.jwt.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	private final String secretKey;
	private final long accessTokenExpireSeconds;
	private final long refreshTokenExpireSeconds;

	private final String bearerType = "Bearer ";


	private final RefreshTokenRepository refreshTokenRepository;
	@Value("${jwt.header.access-token}")
	String accessTokenHeader;

	public JwtTokenProvider(
		@Value("${jwt.secret-key}") String secretKey,
		@Value("${jwt.expire-seconds.access-token}") long accessTokenExpireSeconds,
		@Value("${jwt.expire-seconds.refresh-token}") long refreshTokenExpireSeconds,
		RefreshTokenRepository refreshTokenRepository) {
		this.secretKey = secretKey;
		this.accessTokenExpireSeconds = accessTokenExpireSeconds;
		this.refreshTokenExpireSeconds = refreshTokenExpireSeconds;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public String createAccessToken(Users user) {
		Claims claims = Jwts.claims().setSubject(user.getId().toString());
		claims.put("role", user.getRole());

		log.info("create AccessToken : {}", claims.toString());

		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + accessTokenExpireSeconds * 1000L);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public String createRefreshToken() {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + refreshTokenExpireSeconds * 1000L);

		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public Claims getClaims(String token) {
		log.info("Claims : {}", token);
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		Optional<String> extractAccessToken = Optional.ofNullable(request.getHeader(accessTokenHeader))
			.filter(accessToken -> accessToken.startsWith(bearerType))
			.map(accessToken -> accessToken.replace(bearerType, ""));

		return extractAccessToken;
	}

	@Transactional
	public void updateRefreshToken(Long userId, String refreshToken) {
		refreshTokenRepository.findByUserId(userId)
			.ifPresentOrElse(
				token -> token.updateToken(refreshToken),
				() -> saveRefreshToken(userId, refreshToken)
			);
	}

	@Transactional
	public void saveRefreshToken(Long userId, String refreshToken) {
		RefreshTokenSaveRequestDto refreshRequest = RefreshTokenSaveRequestDto.builder()
			.userId(userId)
			.refreshToken(refreshToken)
			.build();

		refreshTokenRepository.save(RefreshTokenSaveRequestDto.of(refreshRequest));
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		} catch (JwtException | IllegalArgumentException e) {
			throw new TokenInvalidException();
		}
	}

	public Optional<String> resolveToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(accessTokenHeader))
			.filter(accessToken -> accessToken.startsWith(bearerType))
			.map(accessToken -> accessToken.replace(bearerType, ""));
	}

	public JwtAuthenticationToken getAuthentication(String accessToken) {
		Claims claims = getClaims(accessToken);

		log.info("claims : {}", claims.toString());

		Long userId = Long.valueOf(claims.get("sub", String.class));
		String role = claims.get("role", String.class);

		JwtAuthentication principal = new JwtAuthentication(accessToken, userId, role);
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

		log.info("getAuthentication : {}", principal);

		return new JwtAuthenticationToken(authorities, principal, null);
	}
}
