package com.ssafy.a401.artwalk_backend.domain.user.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.user.model.User;
import com.ssafy.a401.artwalk_backend.domain.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"사용자 정보 API"}, description = "사용자 정보 API 입니다.")
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserDataController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";

	private final UserService userService;

	@Operation(summary = "사용자 정보 조회", description = "accessToken과 일치하는 사용자 기본 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "사용자 정보 조회 성공", content = @Content(schema = @Schema(implementation = User.class))),
		@ApiResponse(responseCode = FAIL, description = "사용자 정보 조회 실패")
	})
	@GetMapping("/info")
	public ResponseEntity<User> getMemberInfo(@ApiIgnore Authentication authentication) {
		String email = authentication.getName();
		User user = userService.getUserInfo(email);

		if (user != null) return ResponseEntity.ok().body(user);
		else return ResponseEntity.badRequest().body(null);
	}

	@Operation(summary = "사용자 프로필 사진 조회", description = "사용자 프로필 사진 조회 메서드입니다.")
	@ApiImplicitParam(name = "userId", value = "프로필 사진을 조회할 사용자 ID (예시. ssafy@ssafy.com)", dataType = "String")
	@GetMapping("/info/profile")
	public ResponseEntity<Resource> displayRouteThumbnail(@RequestParam(name = "userId") String userId) {
		ResponseEntity<Resource> response = userService.getProfileImage(userId);
		return response;
	}
}
