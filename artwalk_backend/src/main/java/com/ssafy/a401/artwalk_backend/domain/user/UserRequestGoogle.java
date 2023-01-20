package com.ssafy.a401.artwalk_backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestGoogle {
	private String accessToken;
	private int expireDate;
	private String scope;
	private String tokenType;
	private String idToken;
}
