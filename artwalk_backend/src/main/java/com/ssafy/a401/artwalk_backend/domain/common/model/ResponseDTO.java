package com.ssafy.a401.artwalk_backend.domain.common.model;

import org.springframework.http.HttpHeaders;

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
@Schema(description = "응답 DTO")
public class ResponseDTO {
	@Schema(description = "응답 코드", nullable = false, example = "Ok")
	private String code;

	@Schema(description = "메시지", example = "요청에 따른 응답 메시지")
	private String message;
}
