package com.ssafy.a401.artwalk_backend.domain.user;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class UserService {
	// private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserRepository userRepository;
	// private final PasswordEncoder passwordEncoder;
	// private final TokenProvider tokenProvider;

	// 사용자의 구글 토큰 정보 가지고 오는 곳
	private UserGoogleToken userGoogleToken;
	private UserKakaoToken userKakaoToken;

	// user email과 oAuth의 Token으로 AuthenticationToken 생성한다.
	@Transactional
	public boolean login(String serviceType, String code) {
		// 외부 accessToken이 구글인지 카카오인지 확인한다.
		// api -> accessToken, type을 헤더에 담아 로그인 API를 호출한다.

		// 구글이면
		if (serviceType.equals("google")) {
			try {
				// 일회성 idToken을 이용해 사용자 인증 정보를 받아온다.
				// UserRequestGoogle googleRequestToken = userGoogleToken.getAccessToken(code);
				// 받아온 사용자 인증 정보로 사용자 정보를 가져온다.
				ResponseEntity<String> response = userGoogleToken.getRequestInfo(code);
				// JSON 응답 객체 (String)을 자바 객체로 역직렬화한다.
				UserResponseGoogle userResponseGoogle = userGoogleToken.getUserInfo(response);

				System.out.println(userResponseGoogle.getId() + " " + userResponseGoogle.getEmail() + " " + userResponseGoogle.getPicture());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (serviceType.equals("kakao")) {
			try {
				// 사용자 인증 정보를 받아온다.
				String accessToken = userKakaoToken.getAccessToken(code);
				// JSON 응답 객체 (String)을 자바 객체로 역직렬화한다.
				String result = userKakaoToken.getUserInfo(accessToken);

				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			// Exception 발생
		}
		// 	// accessToken을 검증한다.
		// 	// 검증에 성공한다면
		// 		// 사용자 정보를 가져온다.
		// 		// 사용자 정보가 DB에 존재한다면
		// 			// JWT 토큰만 발급한다.
		// 		// 사용자 정보가 DB에 없다면
		// 			// DB에 저장하고 JWT 토큰을 발급한다.
		// 	// 실패한다면
		// 		// 에러 메시지를 반환한다.
		//
		// // 카카오면
		//
		// UsernamePasswordAuthenticationToken authenticationToken = userRequest.toAuthentication();
		// // 사용자 비밀번호 체크
		// Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		//
		// Token token = tokenProvider.generateToken(authentication);
		//
		// // RefreshToken 저장 로직
		// // RefreshToken refreshToken = RefreshToken.builder()
		// // 	.key(authentication.getName())
		// // 	.value(tokenDto.getRefreshToken())
		// // 	.build();
		// //
		// // refreshTokenRepository.save(refreshToken);
		//
		// return token;
		return true;
	}

	// // 사용자의 응답 유형 확인
	// public boolean validationToken(UserRequest userRequest) {
	// 	String token = userRequest.getToken();
	// 	String type = userRequest.getType();
	//
	// 	if (type.equals("Google")) {
	//
	// 	}
	// 	else if (type.equals("Kakao")) {
	//
	// 	}
	// 	else {
	// 		// Exception 발생
	// 	}
	// }
}
