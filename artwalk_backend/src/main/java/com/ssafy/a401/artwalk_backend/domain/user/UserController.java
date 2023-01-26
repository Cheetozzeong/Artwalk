package com.ssafy.a401.artwalk_backend.domain.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.token.Token;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "사용자", description = "Artwalk 사용자 API 입니다.")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// 사용자 로그인 (가입)
	@Operation(summary = "사용자 로그인", description = "사용자 로그인. 지원 serviceType: kakao\n 로그인 시 헤더에 idToken 추가 필요")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공"),
		@ApiResponse(responseCode = "400", description = "로그인 실패")
	})
	@PostMapping("/login/{serviceType}")
	public ResponseEntity<?> login(@PathVariable String serviceType, @RequestHeader Map<String, Object> header) {
		String idToken = header.get("id-token").toString();
		Token token = userService.login(serviceType, idToken);

		if (token != null) return ResponseEntity.ok(token);
		else return ResponseEntity.badRequest().body("사용자 토큰 발급 실패");
	}

	// 사용자 로그아웃
	@Operation(summary = "사용자 로그아웃", description = "사용자 로그아웃 메서드 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "400", description = "로그아웃 실패")
	})
	@GetMapping("/logout")
	public ResponseEntity<?> login(@RequestHeader Map<String, Object> header) {
		String accessToken = header.get("accessToken").toString();
		// 여기까지 접근했다는 건 이미 인증 토큰이 유효하다는 것이다. (JwtFilter에서 걸러졌음)
		// 사용자 DB의 refreshToken을 사용한다.
		// 기존 인증 토큰은 블랙리스트로 저정해서 만료 확인해줘야 한다. (테이블을 하나 새로 두거나 Redis 사용 필요)

		if(userService.logout(accessToken)) return ResponseEntity.ok("로그아웃 성공");
		else return ResponseEntity.badRequest().body("사용자 정보 확인 실패");
	}

	// 사용자 탈퇴
	@Operation(summary = "사용자 탈퇴", description = "사용자 탈퇴 메서드 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "사용자 탈퇴 성공"),
		@ApiResponse(responseCode = "400", description = "사용자 탈퇴 실패")
	})
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestHeader Map<String, Object> header) {
		String accessToken = header.get("accessToken").toString();

		if(userService.delete(accessToken)) return ResponseEntity.ok("로그아웃 성공");
		else return ResponseEntity.badRequest().body("사용자 정보 확인 실패");
	}
}
