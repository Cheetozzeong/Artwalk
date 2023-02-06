package com.ssafy.a401.artwalk_backend.domain.user.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@ToString
@Entity
@Builder
public class User {

	@Id
	private String userId;

	private String password;

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

	private LocalDateTime regDate;
	private LocalDateTime recentAccess;

	@PrePersist
	public void prePersist() {
		this.regDate = LocalDateTime.now();
		this.recentAccess = this.regDate;
	}

	public User(String userId, String profile, String nickname, int level, int exp) {
		this.userId = userId;
		this.profile = profile;
		this.nickname = nickname;
		this.level = level;
		this.exp = exp;
	}

	public User(String userId, String profile, String nickname, String refreshToken, UserAuthority userAuthority) {
		this.userId = userId;
		this.profile = profile;
		this.nickname = nickname;
		this.refreshToken = refreshToken;
		this.userAuthority = userAuthority;
	}

	public User hashPassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
		return this;
	}

	public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(this.password, plainPassword);
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public void updateRecentAccess() {
		this.recentAccess = LocalDateTime.now();
		System.out.println("recentAccess -----> " + recentAccess);
	}
}
