package com.ssafy.a401.artwalk_backend.domain.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.token.Token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/login/{serviceType}")
	public ResponseEntity<?> login(@PathVariable String serviceType, @RequestHeader Map<String, Object> header) {
		String code = header.get("code").toString();
		System.out.println(code);
		// log.info("serviceType / code -> ", serviceType, code);
		return ResponseEntity.ok(userService.login(serviceType, code));
	}
}
