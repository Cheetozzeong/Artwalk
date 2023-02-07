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
@Schema(description = "기록 요청 DTO")
public class RecordRequestDTO {
	
	@Schema(description = "소요 시간 (단위: 초)", nullable = false, example = "231.12")
	private double duration;

	@Schema(description = "거리 (단위: m)", nullable = false, example = "423.23")
	private double distance;

	@Schema(description = "URL 인코딩된 기록", example = "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f")
	private String geometry;

	@Schema(description = "기록 메모", example = "오운완~^^")
	private String detail;
}
