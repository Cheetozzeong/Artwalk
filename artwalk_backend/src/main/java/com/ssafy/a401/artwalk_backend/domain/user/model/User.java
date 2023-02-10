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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@ToString
@Entity
@Builder
public class User {

	@Id
	@Schema(description = "사용자 ID", nullable = false, example = "ssafy@ssafy.com")
	private String userId;

	@Schema(description = "사용자 비밀번호", nullable = false, example = "password")
	private String password;

	@Column(nullable = true, length = 255)
	private String refreshToken;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("ROLE_USER")
	private UserAuthority userAuthority;

	@Column(nullable = false, length = 30)
	@Schema(description = "사용자 닉네임", nullable = false, example = "이싸피")
	private String nickname;

	@Column
	private String profile;

	@Schema(description = "사용자 레벨", nullable = false, example = "7")
	private int level;
	@Schema(description = "사용자 경험치", nullable = false, example = "151")
	private int exp;

	@Schema(description = "사용자 가입 타입", nullable = false, example = "true | flase")
	private Boolean userType;

	@JsonSerialize(using= LocalDateTimeSerializer.class)
	@JsonDeserialize(using= LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime regDate;

	@JsonSerialize(using= LocalDateTimeSerializer.class)
	@JsonDeserialize(using= LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
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

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public void updateRecentAccess() { this.recentAccess = LocalDateTime.now(); }
}
