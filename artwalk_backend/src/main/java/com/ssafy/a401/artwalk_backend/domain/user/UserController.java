package com.ssafy.a401.artwalk_backend.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.token.Token;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<Token> signup(@RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userService.login(userRequest));
	}
}
