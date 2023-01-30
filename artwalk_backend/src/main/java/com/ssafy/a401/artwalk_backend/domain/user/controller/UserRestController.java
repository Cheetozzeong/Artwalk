package com.ssafy.a401.artwalk_backend.domain.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.common.model.ResponseDTO;
import com.ssafy.a401.artwalk_backend.domain.user.model.User;
import com.ssafy.a401.artwalk_backend.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("user")
public class UserRestController {

	@Autowired
	private UserService userService;

	/** 전체 유저 조회 */
	@Operation(summary = "모든 유저 목록 조회", description = "모든 유저 목록 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "Ok", description = "경로 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = "Fail", description = "경로 목록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/list")
	public ResponseDTO userList(){
		ResponseDTO response = null;
		List<User> users = userService.findAllUser();

		if(users != null){
			response = new ResponseDTO("Ok", users);
		}else{
			response = new ResponseDTO("Fail", null);
		}

		return response;
	}
}
