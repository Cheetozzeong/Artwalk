package com.ssafy.a401.artwalk_backend.domain.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a401.artwalk_backend.domain.common.service.FileService;
import com.ssafy.a401.artwalk_backend.domain.record.repository.RecordRepository;
import com.ssafy.a401.artwalk_backend.domain.route.repository.RouteRepository;
import com.ssafy.a401.artwalk_backend.domain.token.model.Token;
import com.ssafy.a401.artwalk_backend.domain.token.model.TokenProvider;
import com.ssafy.a401.artwalk_backend.domain.user.model.User;
import com.ssafy.a401.artwalk_backend.domain.user.model.UserDeleted;
import com.ssafy.a401.artwalk_backend.domain.user.model.UserKakaoToken;
import com.ssafy.a401.artwalk_backend.domain.user.model.UserResponseKakao;
import com.ssafy.a401.artwalk_backend.domain.user.repository.UserDeletedRepository;
import com.ssafy.a401.artwalk_backend.domain.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final RouteRepository routeRepository;
	private final RecordRepository recordRepository;
	private final UserDeletedRepository userDeletedRepository;
	private final TokenProvider tokenProvider;
	private final UserKakaoToken userKakaoToken;

	private final PasswordEncoder bCryptPasswordEncoder;

	private final FileService fileService;

	@Transactional
	public Token regist(User user) {

		String email = user.getUserId();
		String password = user.getPassword();
		String nickname = user.getNickname();

		return registUser(email, password, "", nickname);
	}

	@Transactional
	public Token useSocialLogin(String serviceType, String idToken) {

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

				return registUser(email, "", picture, nickname);

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

	@Transactional
	public Token useNormalLogin(String email, String password) {

		Optional<User> users = userRepository.findById(email);

		if (users.isPresent()) {
			User user = users.get();

			if (user.getPassword().equals(password)) {
				Authentication authentication = getAuthentication(email, password, "ROLE_USER");
				return getToken(authentication);
			}
//			if (user.checkPassword(password, bCryptPasswordEncoder)) {
//				Authentication authentication = getAuthentication(email, password, "ROLE_USER");
//				return getToken(authentication);
//			}
			else {
				log.info("로그인 실패");
				return null;
			}
		}

		return null;
	}

	@Transactional
	public Token registUser(String email, String password, String picture, String nickname) {
		Authentication authentication = getAuthentication(email, password, "ROLE_USER");
		Token token = getToken(authentication);

		Optional<User> users = userRepository.findById(email);

		// 사용자 이메일이 이미 존재한다면
		if (users.isPresent()) {
			log.info("동일한 이메일이 존재합니다.");

			// Token만 재발급
			User user = users.get();

			// 토큰 재발급 로직 작성 필요
			user.updateRefreshToken(token.getRefreshToken());
			log.info("토큰 값이 갱신되었습니다.");

		} else {
			// 새 사용자 객체
			User user = User.builder()
					.userId(email)
					.password(password)
					.profile(fileService.saveProfileImage(picture, email))
					.nickname(nickname)
					.refreshToken(token.getRefreshToken())
					.build();

//			user.hashPassword(bCryptPasswordEncoder);
			// 새로운 사용자 계정을 등록한다.
			userRepository.save(user);
			log.info("새로운 사용자가 등록되었습니다. email -> ", email);
		}

		return token;
	}

	@Transactional
	public Boolean logout(String email) {
		Optional<User> user = userRepository.findById(email);

		if (user.isPresent()) {
			user.get().updateRefreshToken("");
			return true;
		}
		else {
			log.info("사용자를 확인할 수 없습니다.");
			return false;
		}
	}

	@Transactional
	public Boolean delete(String email) {
		Optional<User> user = userRepository.findById(email);

		if (user.isPresent()) {
			User selectedUser = user.get();
			UserDeleted userDeleted = UserDeleted.builder()
				.userId(selectedUser.getUserId())
				.profile(selectedUser.getProfile())
				.nickname(selectedUser.getNickname())
				.userAuthority(selectedUser.getUserAuthority())
				.build();

			// 탈퇴한 사용자 테이블 등록
			userDeletedRepository.save(userDeleted);
			// 유저 테이블에서 삭제
			userRepository.deleteById(email);
			return true;
		}
		else {
			log.info("사용자를 확인할 수 없습니다.");
			return false;
		}
	}

	public User getUserInfo(String email) {
		Optional<User> user = userRepository.findById(email);

		if (user.isPresent()) {
			User selectedUser = user.get();

			return User.builder()
				.userId(email)
				.profile(selectedUser.getProfile())
				.nickname(selectedUser.getNickname())
				.level(selectedUser.getLevel())
				.exp(selectedUser.getExp())
				.build();
		}
		return null;
	}

	public ResponseEntity<Resource> getProfileImage(String userId){
		return fileService.findProfile(userId);
	}

	public Authentication getAuthentication(String email, String password, String role) {
		// ACCESS 토큰 발급한다. 사용자 권한 -> "ROLE_USER"
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(role));

		// 사용자 인증 객체 세팅한다.
		return new UsernamePasswordAuthenticationToken(email, password, roles);
	}

	// RefreshToken 포함해 새로 발급 받는다.
	public Token getToken(Authentication authentication) {
		return tokenProvider.generateToken(authentication);
	}

	// AccessToken만 발급 받는다.
	public String getNewAccessToken(Authentication authentication) {
		// 어차피 토큰이 여기까지 들어왔으면 유효하기 때문에 따로 확인 필요 없음

		// 토큰 만료 시간 설정
		long now = (new Date()).getTime();
		return tokenProvider.generateAccessToken(authentication, now);
	}

	/**
	 * 모든 사용자 조회
	 */
	// 유저 별 경로 수, 기록 수 추가
	public List<Map<String, Object>> findAllUser() {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> response = new ArrayList<>();

		List<User> users = userRepository.findAll();
		for (User user : users) {
			String userId = user.getUserId();
			long userRouteCount = routeRepository.findByUserId(userId).size();
			long userRecordCount = recordRepository.findByUserId(userId).size();
			Map<String, Object>map = objectMapper.convertValue(user, Map.class);
			map.put("userRouteCount", userRouteCount);
			map.put("userRecordCount", userRecordCount);
			response.add(map);
		}
		return response;
	}

	/** 특정 유저 정보 조회 */
	public Optional<User> findUserDetail(String userId) {
		Optional<User> users = userRepository.findById(userId);

		if (users.isEmpty()) throw new UsernameNotFoundException("회원 정보를 찾지 못했습니다.");
		return users;
	}

	/**
	 * 유저 닉네임 검색
	 */
	public List<User> searchUserNickname(String keyword) {
		List<User> users = userRepository.findByNicknameContaining(keyword);

		return users;
	}

	/**
	 * 유저 아이디 검색
	 */
	public List<User> searchUserIdname(String keyword) {
		List<User> users = userRepository.findByUserIdContaining(keyword);

		return users;
	}

	@Transactional
	public void modifyUserRecentAccess(String email) {
		Optional<User> user = userRepository.findById(email);
		System.out.println(user.get().getUserId());
		user.ifPresent(User::updateRecentAccess);
	}
}
