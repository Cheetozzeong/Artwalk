package com.ssafy.a401.artwalk_backend.global.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ErrorPageController {

	@RequestMapping("/error/4xx")
	public String errorPage(HttpServletRequest request, HttpServletResponse response) {

		System.out.println(request.getHeader("accept"));

		log.info("errorPage 4xx");
		return "error/4123";
	}
}
