package com.ssafy.a401.artwalk_backend.vuejsapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

	@RequestMapping(value = {"/admin/**"})
	public String viewMapping() {
		return "forward:/index.html";
	}

}
