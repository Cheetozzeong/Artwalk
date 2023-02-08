package com.ssafy.a401.artwalk_backend.domain.admin.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.admin.service.AdminService;
import com.ssafy.a401.artwalk_backend.domain.token.model.Token;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"관리자 로그인 API"}, description = "Admin page 로그인 API 입니다.")
@RequestMapping("admin")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://i8a401.p.ssafy.io"}, allowCredentials = "true")
public class AdminRestController {
    private final AdminService adminService;

    @Operation(summary = "관리자 로그인", description = "관리자 로그인 메서드입니다.")
    @PostMapping("/login")
    public ResponseEntity<String> login(String userId, String password) {
        Token token = adminService.login(userId, password);

        // 헤더에 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", token.getAccessToken());
        headers.add("refreshToken", token.getRefreshToken());

        if (token != null) return ResponseEntity.ok().headers(headers).body("SUCCESS");
        else return ResponseEntity.badRequest().body("사용자 토큰 발급 실패");
    }
}