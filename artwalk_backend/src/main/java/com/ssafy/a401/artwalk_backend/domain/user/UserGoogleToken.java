package com.ssafy.a401.artwalk_backend.domain.user;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserGoogleToken {

	RestTemplate restTemplate;
	ObjectMapper objectMapper;

	static final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

	// 접근 토큰을 이용해 구글에 사용자 정보를 받아 올 객체 생성한다.
	public UserRequestGoogle getAccessToken(String code) throws JsonProcessingException {
		UserRequestGoogle userRequestGoogle = objectMapper.readValue(code, UserRequestGoogle.class);
		return userRequestGoogle;
	}

	// 구글에 AccessToken을 전송해 사용자 정보를 받아온다.
	public ResponseEntity<String> getRequestInfo(String code) {

		// Claims claims = Jwts.parserBuilder()

		// 헤더에 접근 토큰을 넣는다.
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "Bearer " + code);
		log.info("Authorization " + code);

		HttpEntity request = new HttpEntity(header);

		ResponseEntity<String> response = restTemplate.exchange(
			GOOGLE_USERINFO_REQUEST_URL,
			HttpMethod.GET,
			request,
			String.class
		);

		log.info("response -> " + response.getBody());

		return response;
	}

	public UserResponseGoogle getUserInfo(ResponseEntity<String> userInfo) throws JsonProcessingException {
		UserResponseGoogle userResponseGoogle = objectMapper.readValue(userInfo.getBody(), UserResponseGoogle.class);
		return userResponseGoogle;
	}
}
