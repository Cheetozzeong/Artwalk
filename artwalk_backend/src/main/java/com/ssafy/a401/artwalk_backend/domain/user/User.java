package com.ssafy.a401.artwalk_backend.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
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

	@Column(nullable = false, length = 20)
	private String password;

	@Column(nullable = false, length = 30)
	private String nickname;

	private String profile;
	private int level;
	private int exp;
}
