package com.ssafy.a401.artwalk_backend.global.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.ssafy.a401.artwalk_backend.global.util.ErrorCode;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		ErrorCode errorCode = (ErrorCode)request.getAttribute("exception");

		//잘못된 타입의 토큰인 경우
		if (errorCode != null) {
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("message", errorCode.getMessage());
			responseJson.addProperty("code", errorCode.getCode());

			response.getWriter().print(responseJson);
		} else {
			// 영문 모를 에러
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}
