package com.ssafy.a401.artwalk_backend.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.ssafy.a401.artwalk_backend.domain.token.Token;
import com.ssafy.a401.artwalk_backend.domain.token.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;
	private final UserKakaoToken userKakaoToken;

	@Transactional
	public Token login(String serviceType, String idToken) {

		// 구글이면
		if (serviceType.equals("google")) {
			try {
				// 구글 로그인 로직 추가 필요
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (serviceType.equals("kakao")) {
			try {

				// 사용자 idToken의 유효성을 검사한다.
				UserResponseKakao userResponseKakao = userKakaoToken.validationToken(idToken);

				log.info("userResponseKakao -> ", userResponseKakao);
				String email = userResponseKakao.getEmail();
				String picture = userResponseKakao.getPicture();
				String nickname = userResponseKakao.getNickname();

				Token token = getToken(email, null, "ROLE_USER");

				Optional<User> users = userRepository.findById(email);

				// 사용자 이메일이 이미 존재한다면
				if (users.isPresent()) {
					log.info("동일한 이메일이 존재합니다.");

					// Token만 재발급
					User user = users.get();

					// 토큰 재발급 로직 작성 필요
					user.setRefreshToken(token.getRefreshToken());
					log.info("토큰 값이 갱신되었습니다.");

				} else {
					// 새 사용자 객체
					User user = User.builder()
						.userid(email)
						.profile(picture)
						.nickname(nickname)
						.refreshToken(token.getRefreshToken())
						.build();

					// 새로운 사용자 계정을 등록한다.
					userRepository.save(user);
					log.info("새로운 사용자가 등록되었습니다. email -> ", email);
				}

				return token;
			} catch (NullPointerException e) {
				log.info("사용자의 idToken을 확인할 수 없습니다.");
				e.printStackTrace();
			} catch (Exception e) {
				log.info("처리 중 에러 발생!");
				e.printStackTrace();
			}
		}

		log.info("요청의 서비스 유형이 올바르지 않습니다.");
		return null;
	}

	public Token getToken(String email, String password, String role) {
		// ACCESS 토큰 발급한다. 사용자 권한 -> "ROLE_USER"
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(role));

		// 사용자 인증 객체 세팅한다.
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, roles);
		Token token = tokenProvider.generateToken(authentication);

		return token;
	}
}
