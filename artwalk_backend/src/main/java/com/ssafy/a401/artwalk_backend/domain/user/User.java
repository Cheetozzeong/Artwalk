package com.ssafy.a401.artwalk_backend.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name="user")
public class User {

	@Id
	private String userid;

	@Column(nullable = true, length = 20)
	private String accessToken;

	@Column(nullable = true, length = 20)
	private String refreshToken;

	@Enumerated(EnumType.STRING)
	private UserAuthority userAuthority;

	@Column(nullable = false, length = 30)
	private String nickname;

	@Column
	private String profile;
	private int level;
	private int exp;

	@Builder
	public User(String userid, String profile, String nickname) {
		this.userid = userid;
		this.profile = profile;
		this.nickname = nickname;
		// this.accessToken = accessToken;
		// this.refreshToken = refreshToken;
		// this.userAuthority = userAuthority;
	}
}
