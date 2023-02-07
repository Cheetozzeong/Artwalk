package com.ssafy.a401.artwalk_backend.domain.user.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.common.model.ResponseDTO;
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

@Api(tags = {"사용자 관리 API"}, description = "admin page 사용자 관리 API 입니다.")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserRestController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";
	private final UserService userService;

	@Operation(summary = "모든 사용자 목록 조회", description = "모든 사용자 목록 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "모든 유저 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "모든 유저 목록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/list")
	public ResponseDTO userList(){
		ResponseDTO response = null;
		List<Map<String, Object>> users = userService.findAllUser();

		if(users != null){
			response = new ResponseDTO(OK, users);
		}else{
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "사용자 정보 조회", description = "특정 사용자 정보 조회 메서드입니다.")
	@ApiImplicitParam(name = "userId", value = "정보를 조회할 사용자 ID (예시. ssafy@ssafy.com)", dataType = "String")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "사용자 정보 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "사용자 정보 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("")
	public ResponseDTO userDetail(@RequestParam(name = "userId", value = "userId") String userId) {
		ResponseDTO response = null;

		try {
			User user = userService.findUserDetail(userId);
			response = new ResponseDTO(OK, user);
		} catch (UsernameNotFoundException e) {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "사용자 닉네임 검색", description = "사용자 닉네임 검색 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "사용자 닉네임 검색 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "사용자 닉네임 검색 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/search")
	public ResponseDTO userSearch(
		@RequestParam(name = "type", value = "type") String type,
		@RequestParam(name = "keyword", value = "keyword") String keyword) {

		ResponseDTO response = null;
		if (type.equals("nickname")) {
			List<Map<String, Object>> nicknames = userService.searchUserNickname(keyword);
			if(nicknames != null){
				response = new ResponseDTO(OK, nicknames);
			}else{
				response = new ResponseDTO(FAIL, null);
			}
		} else if (type.equals("userId")) {
			List<Map<String, Object>> userIds = userService.searchUserIdname(keyword);
			if(userIds != null){
				response = new ResponseDTO(OK, userIds);
			}else{
				response = new ResponseDTO(FAIL, null);
			}
		}
		return response;
	}
}