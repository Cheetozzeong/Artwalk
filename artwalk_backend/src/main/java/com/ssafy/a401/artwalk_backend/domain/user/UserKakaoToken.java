package com.ssafy.a401.artwalk_backend.domain.user;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserKakaoToken {

	RestTemplate restTemplate;

	static final String KAKAO_USERINFO_REQUEST_URL = "https://kauth.kakao.com/oauth/token";

	public UserResponseKakao validationToken(String idToken) {
		DecodedJWT jwtOrigin = JWT.decode(idToken);

		JwkProvider provider = new JwkProviderBuilder("https://kauth.kakao.com")
			.cached(10, 7, TimeUnit.DAYS)
			.build();

		try {
			Jwk jwk = provider.get(jwtOrigin.getKeyId());
			Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(idToken);

			Base64.Decoder decoder = Base64.getUrlDecoder();
			String payload = new String(decoder.decode(jwt.getPayload()));

			System.out.println("payload -> " + payload);

			ObjectMapper objectMapper = new ObjectMapper();
			// 2.8부터 JsonObject 따로 생성 안함
			// JsonObject jsonObject = JsonParser.parseString(payload).getAsJsonObject();
			UserResponseKakao userResponseKakao = objectMapper.readValue(payload, UserResponseKakao.class);

			System.out.println(userResponseKakao);
			return userResponseKakao;
		} catch (JWTVerificationException e) {
			log.info("토큰이 만료되었거나 검증에 실패했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 접근 토큰을 이용해 구글에 사용자 정보를 받아 올 객체 생성한다.
	public String getAccessToken(String code) {

		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//
		// LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		// params.add("grant_type", "authorization_code");
		// params.add("client_id", "24cbc222d640286c932197f27ec1b8db");
		// params.add("redirect_uri", "http://localhost:8080/oauth");
		// params.add("code", code);
		//
		// HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		//
		// System.out.println("request --> " + request);
		// ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_USERINFO_REQUEST_URL, request, String.class);
		//
		// try {
		// 	return objectMapper.readValue(response.getBody(), UserResponseKakao.class).getAccess_token();
		// } catch (JsonProcessingException e) {
		// 	e.printStackTrace();
		// }
		return null;
	}

	// public String getUserInfo(String idToken) {
	//
	// 	// HttpHeaders headers = new HttpHeaders();
	// 	// headers.set("Authorization", "Bearer " + accessToken);
	// 	// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	// 	//
	// 	// LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	// 	//
	// 	// HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
	// 	//
	// 	// String url = "https://kapi.kakao.com/v2/user/me";
	//
	//
	// 	// return restTemplate.postForObject(url, request, String.class);
	// }
}
