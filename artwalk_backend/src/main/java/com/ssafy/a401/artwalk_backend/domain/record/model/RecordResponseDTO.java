package com.ssafy.a401.artwalk_backend.domain.record.model;

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
@Schema(description = "기록 응답 DTO")
public class RecordResponseDTO {
	@Schema(description = "응답 코드", nullable = false, example = "Ok")
	private String code;

	@Schema(description = "기록 데이터", example = "요청에 따른 기록 데이터")
	private Record record;
}
