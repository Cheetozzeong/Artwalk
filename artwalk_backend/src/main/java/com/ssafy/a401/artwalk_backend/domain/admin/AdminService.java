package com.ssafy.a401.artwalk_backend.domain.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.a401.artwalk_backend.domain.token.Token;
import com.ssafy.a401.artwalk_backend.domain.token.TokenProvider;
import com.ssafy.a401.artwalk_backend.domain.user.UserKakaoToken;
import com.ssafy.a401.artwalk_backend.domain.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;
	private final UserService userService;

	@Transactional
	public Token login(String userid, String password) {
		Optional<Admin> admins = adminRepository.findById(userid);

		if (admins.isPresent()) {
			Admin admin = admins.get();
			if (password.equals(admin.getPassword())) {
				Token token = userService.getToken(userid, password, "ROLE_ADMIN");
				admin.setRefreshToken(token.getRefreshToken());
				return token;
			}
			else {
				log.info("패스워드 불일치");
			}
		}
		else {
			log.info("관리자 아이디 틀림");
		}

		return null;
	}
}