package com.ssafy.a401.artwalk_backend.domain.token.model;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
// @PropertySource("classpath:application.properties")
public class TokenProvider {

	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "Bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 12; // 만료 12시간
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 60; // 만료 60일

	private static Key key;

	public TokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	// 토큰 생성
	public Token generateToken(Authentication authentication) {

		// 토큰 만료 시간 설정
		long now = (new Date()).getTime();

		String accessToken = generateAccessToken(authentication, now);
		String refreshToken = generateRefreshToken(authentication, now);

		// 생성한 Token 객체 반환
		return Token.builder()
				.grantType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public String getAuthorities(Authentication authentication) {
		return authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
	}

	public String generateAccessToken(Authentication authentication, Long now) {
		String authorities = getAuthorities(authentication);

		// 생성한 Token 객체 반환
		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITIES_KEY, authorities)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
			.compact();
	}

	public String generateRefreshToken(Authentication authentication, Long now) {
		String authorities = getAuthorities(authentication);

		// 생성한 Token 객체 반환
		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITIES_KEY, authorities)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
			.compact();
	}

	// 받은 토큰으로 사용자 인증 권한 가져오기
	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);

		if (claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException("권한 정보가 없습니다.");
		}

		// claim에서 권한 정보 가져오기
		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get(AUTHORITIES_KEY)
				.toString().split(","))
				// SimpleGrantedAuthority: Security에서 제공하는 클래스 객체
				// SimpleGrantedAuthority::new == SimpleGrantedAuthority = new Simple~
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		// claim에서 가져온 아이디와 비밀번호로 User 객체 생성한다.
		UserDetails principal = new User(claims.getSubject(), "", authorities);

		// 인증 토큰을 만들어서 반환한다 -> 이 인증 객체를 AuthenticationManager의 구현체인 AuthenticationProvider에 전달한다.
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public boolean validateToken(String token) throws IllegalArgumentException, SignatureException, UnsupportedJwtException {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.info("토큰이 만료되었습니다.");
			return false;
		}
	}

	public boolean isNeedToUpdateRefreshToken(String token) {
		try {
			Date expireDate = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getExpiration();

			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.DATE, 7);

			Date after7dayFromToday = calendar.getTime();

			// 토큰이 7일 이내 만료된다면
			if (expireDate.before(after7dayFromToday)) {
				log.info("토큰이 7일 이내 만료됩니다.");
				return true;
			}
		} catch (TokenExpiredException e) {
			return true;
		}
		return false;
	}

	public String getUserEmail(String token) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
