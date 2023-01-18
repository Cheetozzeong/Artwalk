package com.ssafy.a401.artwalk_backend.domain.user;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.a401.artwalk_backend.domain.token.Token;
import com.ssafy.a401.artwalk_backend.domain.token.TokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserRepository userRepository;
	// private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	// user email과 oAuth의 Token으로 AuthenticationToken 생성한다.
	@Transactional
	public Token login(UserRequest userRequest) {
		// 외부 accessToken이 구글인지 카카오인지 확인한다.

		// 구글이면
			// accessToken을 검증한다.
			// 검증에 성공한다면
				// 사용자 정보를 가져온다.
				// 사용자 정보가 DB에 존재한다면
					// JWT 토큰만 발급한다.
				// 사용자 정보가 DB에 없다면
					// DB에 저장하고 JWT 토큰을 발급한다.
			// 실패한다면
				// 에러 메시지를 반환한다.

		// 카카오면

		UsernamePasswordAuthenticationToken authenticationToken = userRequest.toAuthentication();
		// 사용자 비밀번호 체크
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		Token token = tokenProvider.generateToken(authentication);

		// RefreshToken 저장 로직
		// RefreshToken refreshToken = RefreshToken.builder()
		// 	.key(authentication.getName())
		// 	.value(tokenDto.getRefreshToken())
		// 	.build();
		//
		// refreshTokenRepository.save(refreshToken);

		return token;
	}
}
