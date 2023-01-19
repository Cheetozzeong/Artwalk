package com.ssafy.a401.artwalk_backend.domain.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
	private String grantType;
	private String accessToken;
	private String refreshToken;
	private Long accessTokenExpireTime;
}
