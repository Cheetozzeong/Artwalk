package com.ssafy.a401.artwalk_backend.domain.user.model;

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
@Schema(description = "사용자 요청 DTO")
public class UserRequestDTO {
	@Schema(description = "사용자 ID", nullable = false, example = "ssafy@ssafy.com")
	private String userId;

	@Schema(description = "사용자 비밀번호", nullable = false, example = "password")
	private String password;

	@Schema(description = "사용자 닉네임", nullable = false, example = "이싸피")
	private String nickname;
}
