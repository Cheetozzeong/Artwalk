package com.ssafy.a401.artwalk_backend.domain.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;

@Api(tags = {"관리자 페이지 이동 API"}, description = "Admin page 이동 API 입니다.")
@RequestMapping("admin")
@Controller
public class AdminController {
	@Operation(summary = "다른 경로 접근", description = "다른 경로 접근시 index 페이지로 이동합니다.")
	@GetMapping("**")
	public String viewMapping() {
		return "forward:/index.html";
	}
}
