package com.ssafy.a401.artwalk_backend.domain.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api(tags = {"관리자 페이지 이동 API"}, description = "ADMIN PAGE 이동 API 입니다.")
@RequestMapping("admin")
@Controller
public class AdminController {
	@GetMapping("**")
	public String viewMapping() {
		return "forward:/index.html";
	}
}
