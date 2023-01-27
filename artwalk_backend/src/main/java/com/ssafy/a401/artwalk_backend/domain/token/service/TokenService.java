package com.ssafy.a401.artwalk_backend.domain.token.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ssafy.a401.artwalk_backend.domain.token.model.Token;
import com.ssafy.a401.artwalk_backend.domain.user.model.User;
import com.ssafy.a401.artwalk_backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
// @PropertySource("classpath:application.properties")
public class TokenService {

	private final UserRepository userRepository;

	public boolean findByRefreshToken(String refreshToken) {
		return userRepository.countByRefreshToken(refreshToken) == 1;
	}

	@Transactional
	public void setNewRefreshToken(String email, Token token) {
		Optional<User> user = userRepository.findById(email);

		// 사용자 이메일이 이미 존재한다면
		if (user.isPresent()) {
			User selectedUser = user.get();
			selectedUser.setRefreshToken(token.getRefreshToken());
			log.info(selectedUser.getUserId(), " 새 토큰 생성");
		}
	}

	// 토큰에서 회원 정보 추출
	// Email로 권한 정보 가져오기
	public List<GrantedAuthority> getRoles(String email) {
		String role = userRepository.findById(email).get().getUserAuthority().toString();
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(role));
		return roles;
	}
}
