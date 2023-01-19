package com.ssafy.a401.artwalk_backend.domain.user;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserKakaoToken {

	RestTemplate restTemplate;
	ObjectMapper objectMapper;

	static final String KAKAO_USERINFO_REQUEST_URL = "https://kauth.kakao.com/oauth/token";

	// 접근 토큰을 이용해 구글에 사용자 정보를 받아 올 객체 생성한다.
	public String getAccessToken(String code) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "24cbc222d640286c932197f27ec1b8db");
		params.add("redirect_uri", "http://localhost:8080" + "/oauth2/code/kakao");
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		System.out.println("request --> " + request);
		ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_USERINFO_REQUEST_URL, request, String.class);

		try {
			return objectMapper.readValue(response.getBody(), UserResponseKakao.class).getAccess_token();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUserInfo(String accessToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		String url = "https://kapi.kakao.com/v2/user/me";

		return restTemplate.postForObject(url, request, String.class);
	}
}
