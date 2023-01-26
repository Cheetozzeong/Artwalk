package com.ssafy.a401.artwalk_backend.global.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.a401.artwalk_backend.domain.token.Token;
import com.ssafy.a401.artwalk_backend.domain.token.TokenProvider;
import com.ssafy.a401.artwalk_backend.domain.user.User;
import com.ssafy.a401.artwalk_backend.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	public static final String AUTHORIZATION_HEADER = "AccessToken";
	public static final String REFRESH_HEADER = "RefreshToken";
	public static final String BEARER_PREFIX = "Bearer ";

	private final TokenProvider tokenProvider;

	// 토큰의 인증 정보를 현재 스레드의 SecurityContext에 저장한다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		// Request Header에서 토큰을 꺼내 파싱한다.
		String accessToken = resolveToken(request);
		String refreshToken = resolveRefreshToken(request);

		// 토큰 유효성 검사한다.
		if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
			Authentication authentication = tokenProvider.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		// AccessToken은 만료됐지만 refreshToken이 유효하다면
		else if (!tokenProvider.validateToken(accessToken) && refreshToken != null) {
			boolean isRefreshToken = tokenProvider.findByRefreshToken(refreshToken);

			// 사용자 RefreshToken이 존재한다면
			if (isRefreshToken) {
				String email = tokenProvider.getUserEmail(refreshToken);
				List<GrantedAuthority> roles = tokenProvider.getRoles(email);

				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, roles);
				Token token = tokenProvider.generateToken(authentication);
				
				// DB에 토큰 세팅
				tokenProvider.setNewRefreshToken(email, token);
				
				authentication = tokenProvider.getAuthentication(token.getAccessToken());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			else {
				// 권한 없음
			}
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		// 인증 헤더를 가지고 온다.
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

		// 인증 헤더가 존재하고, 인증 헤더 내용이 BEARER_PREFIX로 시작한다면 인증 정보를 파싱해서 리턴한다.
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}

		return null;
	}

	private String resolveRefreshToken(HttpServletRequest request) {
		// 인증 헤더를 가지고 온다.
		String bearerToken = request.getHeader(REFRESH_HEADER);

		// 인증 헤더가 존재하고, 인증 헤더 내용이 BEARER_PREFIX로 시작한다면 인증 정보를 파싱해서 리턴한다.
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}

		return null;
	}
}
