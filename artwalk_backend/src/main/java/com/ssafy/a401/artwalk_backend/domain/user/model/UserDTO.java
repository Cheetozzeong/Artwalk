package com.ssafy.a401.artwalk_backend.domain.user.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "사용자 DTO")
public class UserDTO {
	@Schema(description = "사용자 ID", nullable = false, example = "ssafy@ssafy.com")
	private String userId;

	@Schema(description = "비밀번호", nullable = false, example = "password")
	private String password;

	@Schema(description = "리프레쉬 토큰", nullable = false, example = "token")
	private String refreshToken;

	@Schema(description = "사용자 권한", nullable = false, example = "ROLE_USER")
	private UserAuthority userAuthority;

	@Schema(description = "닉네임", nullable = false, example = "이싸피")
	private String nickname;

	@Schema(description = "프로필 사진", nullable = false, example = "/info/profile?userId=ssafy@ssafy.com")
	private String profile;

	@Schema(description = "레벨", nullable = false, example = "7")
	private int level;
	
	@Schema(description = "경험치", nullable = false, example = "151")
	private int exp;

	@Schema(description = "가입 타입", nullable = false, example = "true(social) | false(app)")
	private Boolean userType;
	
	@Schema(description = "가입 날짜", nullable = false, example = "2023/02/06 17:23:45")
    private LocalDateTime regDate;

	@Schema(description = "마지막 접속 날짜", nullable = false, example = "2023/02/06 17:23:45")
	private LocalDateTime recentAccess;
}
