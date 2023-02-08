package com.ssafy.a401.artwalk_backend.domain.common.model;

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
@Schema(description = "개수 응답 DTO")
public class CountResponseDTO {
	@Schema(description = "응답 코드", nullable = false, example = "Ok")
	private String code;

	@Schema(description = "데이터 개수", example = "요청에 따른 데이터 개수")
	private long count;
}
