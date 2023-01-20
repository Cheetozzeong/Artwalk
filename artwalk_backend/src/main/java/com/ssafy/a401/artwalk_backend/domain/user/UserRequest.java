package com.ssafy.a401.artwalk_backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
	private String token;
	private String type;

	public User user(String token) {
		return User.builder()
			.token(token)
			.userAuthority(UserAuthority.ROLE_USER)
			.build();
	}

	// public UsernamePasswordAuthenticationToken toAuthentication() {
	// 	return new UsernamePasswordAuthenticationToken(userid, token);
	// }
}
