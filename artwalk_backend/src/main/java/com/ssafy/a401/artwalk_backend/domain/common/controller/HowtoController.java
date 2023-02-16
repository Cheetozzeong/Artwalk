package com.ssafy.a401.artwalk_backend.domain.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;

@Api(tags = {"시용 설명 페이지 API"}, description = "사용 설명 페이지 이동 API 입니다.")
@RequestMapping("howto")
@Controller
public class HowtoController {
	@Operation(summary = "사용 설명", description = "사용 설명 페이지 이동 메서드입니다.")
	@GetMapping("")
	public String howtoMapping() {
		return "howto/howto";
	}
}
