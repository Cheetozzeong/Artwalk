package com.ssafy.a401.artwalk_backend.domain.user.model;

import java.util.List;
import java.util.Map;

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
@Schema(description = "사용자 목록 응답 DTO")
public class UserListResponseDTO {
	@Schema(description = "응답 코드", nullable = false, example = "Ok")
	private String code;

	@Schema(description = "사용자 목록 데이터", example = "요청에 따른 사용자 목록 데이터")
	private List<Map<String, Object>> users;
}
