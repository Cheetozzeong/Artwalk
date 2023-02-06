package com.ssafy.a401.artwalk_backend.domain.user.controller;

import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.route.model.Route;
import com.ssafy.a401.artwalk_backend.domain.token.model.Token;
import com.ssafy.a401.artwalk_backend.domain.user.model.User;
import com.ssafy.a401.artwalk_backend.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Tag(name = "사용자", description = "Artwalk 사용자 API 입니다.")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserDataController {

	private final UserService userService;

	// 사용자 로그인 (가입)
	@Operation(summary = "사용자 정보", description = "사용자 기본 정보를 받습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "400", description = "실패")
	})

	@GetMapping("/info")
	public ResponseEntity<User> getMemberInfo(Authentication authentication) {
		String email = authentication.getName();
		User user = userService.getUserInfo(email);

		if (user != null) return ResponseEntity.ok().body(user);
		else return ResponseEntity.badRequest().body(null);
	}

	@Operation(summary = "사용자 프로필 사진 조회", description = "사용자 프로필 사진 조회 메서드입니다.")
	@GetMapping("/info/profile")
	public ResponseEntity<Resource> displayRouteThumbnail(@RequestParam(name = "userId", value = "userId") String userId) {
		ResponseEntity<Resource> response = userService.getProfileImage(userId);
		return response;
	}
}
