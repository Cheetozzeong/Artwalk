package com.ssafy.a401.artwalk_backend.domain.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping("/view")
    public String viewMapping() {

        return "forward:/index.html";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(String userid, String password) {
        System.out.println("userInfo -> " + userid + " " + password);
        // 관리자 로그인 화면
        return ResponseEntity.ok(adminService.login(userid, password));
    }
}