package com.ssafy.a401.artwalk_backend.global.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.a401.artwalk_backend.domain.token.model.Token;
import com.ssafy.a401.artwalk_backend.domain.token.model.TokenProvider;
import com.ssafy.a401.artwalk_backend.domain.token.service.TokenService;
import com.ssafy.a401.artwalk_backend.global.error.ErrorCode;

import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	public static final String AUTHORIZATION_HEADER = "AccessToken";
	public static final String REFRESH_HEADER = "RefreshToken";
	public static final String BEARER_PREFIX = "Bearer ";

	private final TokenProvider tokenProvider;
	private final TokenService tokenService;

	// 토큰의 인증 정보를 현재 스레드의 SecurityContext에 저장한다.
	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
		@NotNull FilterChain filterChain) throws ServletException, IOException {

		try {
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
				boolean isRefreshToken = tokenService.findByRefreshToken(refreshToken);

				// 사용자 RefreshToken이 존재하고 유효하다면
				if (isRefreshToken && tokenProvider.validateToken(refreshToken)) {

					// 인증 객체 생성
					Authentication authentication = tokenProvider.getAuthentication(accessToken);

					String newAccessToken = "";
					String newRefreshToken = refreshToken;

					// 7일 이내 토큰이 만료된다면 재발급
					if (tokenProvider.isNeedToUpdateRefreshToken(refreshToken)) {
						String email = tokenProvider.getUserEmail(refreshToken);
						Token token = tokenProvider.generateToken(authentication);

						newAccessToken = token.getAccessToken();
						newRefreshToken = token.getRefreshToken();

						// DB에 토큰 세팅
						tokenService.setNewRefreshToken(email, newRefreshToken);

					}
					// 아니라면 AccssToken만 재발급 한다.
					else {
						long now = (new Date()).getTime();
						newAccessToken = tokenProvider.generateAccessToken(authentication, now);
					}

					// 헤더에 토큰 세팅
					response.setHeader("accessToken", newAccessToken);
					response.setHeader("refreshToken", newRefreshToken);
					response.setStatus(HttpServletResponse.SC_CREATED);

					authentication = tokenProvider.getAuthentication(newAccessToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					log.info("토큰 재발급 권한이 만료되었습니다.");
					request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN);
				}
			} else {
				log.info("접근 토큰 권한이 만료되었습니다.");
				request.setAttribute("exception", ErrorCode.EXPIRED_ACCESS_TOKEN);
			}
		}
		catch (IllegalArgumentException e) {
			log.info("잘못된 JWT 서명입니다.");
			request.setAttribute("exception", ErrorCode.ILLEGAL_TOKEN);
		} catch (SignatureException e) {
			log.info("JWT 시그니처가 일치하지 않습니다.");
			request.setAttribute("exception", ErrorCode.ILLEGAL_TOKEN);
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
			request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN);
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
