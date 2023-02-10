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
@Schema(description = "기록 공유이미지 요청 DTO")
public class RecordImageRequestDTO {
	@Schema(description = "Polyline 굵기", nullable = false, example = "5")
	private int polyLineWidth;

	@Schema(description = "Polyline 색상", nullable = false, example = "#ff0000")
	private String polyLineColor;

	@Schema(description = "저장할 지도 경계 최소 경도", nullable = false, example = "126.9138")
	private double minLon;

	@Schema(description = "저장할 지도 경계 최소 위도", nullable = false, example = "37.4734")
	private double minLat;

	@Schema(description = "저장할 지도 경계 최대 경도", nullable = false, example = "127.0732")
	private double maxLon;

	@Schema(description = "저장할 지도 경계 최대 위도", nullable = false, example = "37.5577")
	private double maxLat;

}
