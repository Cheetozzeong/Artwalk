package com.ssafy.a401.artwalk_backend.domain.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
	private String userid;
	private String token;
	private String type;

	public User user(String token) {
		return User.builder()
			.userid(userid)
			.token(token)
			.userAuthority(UserAuthority.ROLE_USER)
			.build();
	}

	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(userid, token);
	}
}
