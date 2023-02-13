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

	@Schema(description = "저장할 지도 중심 경도", nullable = false, example = "126.9138")
	private double centerLon;

	@Schema(description = "저장할 지도 중심 위도", nullable = false, example = "37.4734")
	private double centerLat;

	@Schema(description = "저장할 지도 확대 정도", nullable = false, example = "12")
	private double zoom;

	@Schema(description = "저장할 지도 회전 정도", nullable = false, example = "90")
	private double bearing;

}
