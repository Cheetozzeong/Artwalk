package com.ssafy.a401.artwalk_backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponseKakao {
	public String token_type;
	public String access_token;
	public String id_token;
	public Integer expire_in;
	public String refresh_token;
	public Integer refresh_token_expires_in;
	public String scope;
}
