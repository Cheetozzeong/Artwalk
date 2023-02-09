package com.ssafy.a401.artwalk_backend.domain.policy.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("policy")
@Controller
public class PolicyController {

	@GetMapping("/service")
	public String servicePolicyMapping() {
		return "policy/servicePolicy";
	}

	@GetMapping("/privacy")
	public String privacyPolicyMapping() {
		return "policy/privacyPolicy";
	}
}
