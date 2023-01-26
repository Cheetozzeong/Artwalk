package com.ssafy.a401.artwalk_backend.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "deleteuser")
public class UserDeleted {

	@Id
	private String userId;

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
	public UserDeleted(String userId, String profile, String nickname, UserAuthority userAuthority) {
		this.userId = userId;
		this.profile = profile;
		this.nickname = nickname;
		this.userAuthority = userAuthority;
	}
}
