package com.ssafy.a401.artwalk_backend.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponseKakao {
	public String iss;
	public String aud;
	public String sub;
	public String iat;
	public String auth_time;
	public String exp;
	public String nonce;
	public String nickname;
	public String picture;
	public String email;
}
