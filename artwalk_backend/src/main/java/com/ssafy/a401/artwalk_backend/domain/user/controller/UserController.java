package com.ssafy.a401.artwalk_backend.domain.user.controller;

import java.util.Map;

import com.ssafy.a401.artwalk_backend.domain.user.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.a401.artwalk_backend.domain.token.model.Token;
import com.ssafy.a401.artwalk_backend.domain.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"사용자 로그인 API"}, description = "사용자 로그인 API 입니다.")
@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "사용자 가입", description = "사용자 가입. Artwalk 자체적으로 사용자 가입한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "가입 성공"),
		@ApiResponse(responseCode = "400", description = "가입 실패")
	})
	@PostMapping("/reg/artwalk")
	public ResponseEntity<?> userNormalSave(@RequestBody User user) {
		Token token = userService.addNormalUser(user);
		if (token == null) return ResponseEntity.badRequest().body("사용자 아이디 중복");

		HttpHeaders headers = userHeader(token);
		return ResponseEntity.ok().headers(headers).body("SUCCESS");
	}

	// 사용자 로그인 (가입)
	@Operation(summary = "사용자 로그인", description = "사용자 로그인. 지원 serviceType: kakao\n 로그인 시 헤더에 idToken 추가 필요")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공"),
		@ApiResponse(responseCode = "400", description = "로그인 실패")
	})
	@PostMapping("/login/{serviceType}")
	public ResponseEntity<?> userSocialSave(@PathVariable String serviceType, @RequestHeader Map<String, Object> header) {
		String idToken = header.get("id-token").toString();
		Token token = userService.addSocialUser(serviceType, idToken);
		if (token == null) return ResponseEntity.badRequest().body("사용자 토큰 발급 실패");

		HttpHeaders headers = userHeader(token);
		return ResponseEntity.ok().headers(headers).body("SUCCESS");
	}

	// 사용자 로그인 (폼으로 로그인)
	@Operation(summary = "사용자 로그인", description = "사용자 로그인. 폼 데이터를 이용해 로그인")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그인 성공"),
			@ApiResponse(responseCode = "400", description = "로그인 실패")
	})
	@PostMapping("/login/artwalk")
	public ResponseEntity<?> userLogin(@RequestBody User user) {

		Token token = userService.useNormalLogin(user.getUserId(), user.getPassword());
		if (token == null) return ResponseEntity.badRequest().body("사용자 토큰 발급 실패");
		userService.modifyUserRecentAccess(user.getUserId());

		HttpHeaders headers = userHeader(token);
		return ResponseEntity.ok().headers(headers).body("SUCCESS");
	}

	// 어플리케이션 접속 시 AccessToken 새로 발급한다.
	@Operation(summary = "사용자 Access Token 갱신", description = "사용자가 어플리케이션 실행 시 토큰 갱신한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "갱신 성공"),
		@ApiResponse(responseCode = "400", description = "갱신 실패")
	})
	@PostMapping("/connect")
	public ResponseEntity<?> userConnectDetails(Authentication authentication) {
		// 새 AccessToken 발급한다.
		String newAccessToken = userService.getNewAccessToken(authentication);
		if (newAccessToken == null) return ResponseEntity.badRequest().body("사용자 접속 토큰 갱신 실패");

		userService.modifyUserRecentAccess(authentication.getName());

		// 헤더에 담는다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("accessToken", newAccessToken);

		return ResponseEntity.ok().headers(headers).body("SUCCESS");
	}

	// 사용자 로그아웃
	@Operation(summary = "사용자 로그아웃", description = "사용자 로그아웃 메서드 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "400", description = "로그아웃 실패")
	})
	@PostMapping("/logout")
	public ResponseEntity<?> userLogout(Authentication authentication) {
		if(userService.logoutUser(authentication.getName())) return ResponseEntity.ok("로그아웃 성공");
		else return ResponseEntity.badRequest().body("사용자 정보 확인 실패");
	}

	// 사용자 탈퇴
	@Operation(summary = "사용자 탈퇴", description = "사용자 탈퇴 메서드 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "사용자 탈퇴 성공"),
		@ApiResponse(responseCode = "400", description = "사용자 탈퇴 실패")
	})
	@DeleteMapping("/remove")
	public ResponseEntity<?> userRemove(Authentication authentication) {
		if(userService.removeUser(authentication.getName())) return ResponseEntity.ok("탈퇴 성공");
		else return ResponseEntity.badRequest().body("사용자 정보 확인 실패");
	}

	// 사용자 토큰을 헤더에 세팅한다.
	private HttpHeaders userHeader(Token token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("accessToken", token.getAccessToken());
		headers.add("refreshToken", token.getRefreshToken());
		return headers;
	}
}
