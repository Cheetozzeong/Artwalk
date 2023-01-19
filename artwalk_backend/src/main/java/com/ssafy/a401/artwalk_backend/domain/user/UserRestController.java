package com.ssafy.a401.artwalk_backend.domain.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserRestController {

	@GetMapping("user")
	public String helloUser() {
		System.out.println("Hello User!");
		return "Hello User";
	}
}
