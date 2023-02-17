package com.ssafy.a401.artwalk_backend.domain.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.user.model.UserDTO;
import com.ssafy.a401.artwalk_backend.domain.user.model.UserListResponseDTO;
import com.ssafy.a401.artwalk_backend.domain.user.model.UserResponseDTO;
import com.ssafy.a401.artwalk_backend.domain.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"사용자 관리 API"}, description = "Admin page 사용자 관리 API 입니다.")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserRestController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";
	private final UserService userService;

	@Operation(summary = "모든 사용자 목록 조회", description = "모든 사용자 목록 조회 메서드입니다.")
	@GetMapping("/list")
	public ResponseEntity<UserListResponseDTO> userList(){
		List<Map<String, Object>> users = userService.findAllUser();

		if(users != null) return ResponseEntity.ok().body(new UserListResponseDTO(OK, users));
		else return ResponseEntity.badRequest().body(new UserListResponseDTO(FAIL, null));
	}

	@Operation(summary = "사용자 정보 조회", description = "특정 사용자 정보 조회 메서드입니다.")
	@ApiImplicitParam(name = "userId", value = "정보를 조회할 사용자 ID (예시. ssafy@ssafy.com)", dataType = "String")
	@GetMapping("")
	public ResponseEntity<UserResponseDTO> userDetail(@RequestParam(name = "userId") String userId) {
		try {
			UserDTO userDTO = userService.findUserDetail(userId);
			return ResponseEntity.ok().body(new UserResponseDTO(OK, userDTO));
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.badRequest().body(new UserResponseDTO(FAIL, null));
		}
	}

	@Operation(summary = "사용자 닉네임/아이디 검색", description = "사용자 닉네임/아이디 검색 메서드입니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "검색 옵션 - nickname(닉네임), userId(사용자 아이디)", dataType = "String"),
		@ApiImplicitParam(name = "keyword", value = "검색 키워드", dataType = "String")
	})
	@GetMapping("/search")
	public ResponseEntity<UserListResponseDTO> userSearch(@RequestParam(name = "type") String type, @RequestParam(name = "keyword") String keyword) {
		List<Map<String, Object>> data = null;

		if (type.equals("nickname")) data = userService.searchUserNickname(keyword);
		else if (type.equals("userId")) data = userService.searchUserIdname(keyword);

		if(data != null) return ResponseEntity.ok().body(new UserListResponseDTO(OK, data));
		else return ResponseEntity.badRequest().body(new UserListResponseDTO(FAIL, null));
	}
}