package com.ssafy.a401.artwalk_backend.domain.token;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
// @PropertySource("classpath:application.properties")
// 구글 토큰의 무결성을 검증하는 클래스
public class TokenVerifier {

	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "Bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 만료 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 만료 7일

	private Key key;

	public TokenVerifier(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	// 토큰 생성
	public Token generateToken(Authentication authentication) {
		// 권한 목록을 String 형태로
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		// 토큰 만료 시간 설정
		long now = (new Date()).getTime();
		Date accessTokenExpireTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

		// Access Token 생성
		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITIES_KEY, authorities)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(accessTokenExpireTime)
			.compact();

		// Refresh Token 생성
		String refreshToken = Jwts.builder()
			.setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		// 생성한 Token 객체 반환
		return Token.builder()
				.grantType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.accessTokenExpireTime(accessTokenExpireTime.getTime())
			.build();
	}

	// 받은 토큰으로 사용자 인증 권한 가져오기
	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);

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

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token);
			return true;
		} catch (IllegalArgumentException e) {
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 서명입니다.");
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		}
		return false;
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
