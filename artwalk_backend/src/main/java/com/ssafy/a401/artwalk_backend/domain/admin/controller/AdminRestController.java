package com.ssafy.a401.artwalk_backend.domain.admin.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.admin.service.AdminService;
import com.ssafy.a401.artwalk_backend.domain.token.model.Token;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// @Tag(name = "관리자", description = "Artwalk 관리자 API 입니다.")
@RequestMapping("admin")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://i8a401.p.ssafy.io"}, allowCredentials = "true")
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping("/view")
    public String viewMapping() {

        return "forward:/index.html";
    }

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