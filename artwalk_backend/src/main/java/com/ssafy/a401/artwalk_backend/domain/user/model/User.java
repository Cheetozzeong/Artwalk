package com.ssafy.a401.artwalk_backend.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@DynamicInsert
@ToString
@Entity
public class User {

	@Id
	private String userId;

	@Column(nullable = true, length = 255)
	private String refreshToken;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("ROLE_USER")
	private UserAuthority userAuthority;

	@Column(nullable = false, length = 30)
	private String nickname;

	@Column
	private String profile;
	private int level;
	private int exp;

	@Builder
	public User(String userId, String profile, String nickname, String refreshToken, UserAuthority userAuthority) {
		this.userId = userId;
		this.profile = profile;
		this.nickname = nickname;
		this.refreshToken = refreshToken;
		this.userAuthority = userAuthority;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
