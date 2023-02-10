package com.ssafy.a401.artwalk_backend.domain.policy.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;

@Api(tags = {"정책 페이지 API"}, description = "정책 페이지 이동 API 입니다.")
@RequestMapping("policy")
@Controller
public class PolicyController {

	@Operation(summary = "서비스이용약관", description = "서비스이용약관 페이지 이동 메서드입니다.")
	@GetMapping("/service")
	public String servicePolicyMapping() {
		return "policy/servicePolicy";
	}

	@Operation(summary = "개인정보처리방침", description = "개인정보처리방침 페이지 이동 메서드입니다.")
	@GetMapping("/privacy")
	public String privacyPolicyMapping() {
		return "policy/privacyPolicy";
	}
}
