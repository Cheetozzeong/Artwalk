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

	@Schema(description = "polyline 인코딩된 기록", example = "}e{cFemjfWaA}EdEgBh@yAw@yDlPeG??SF{@_EuAf@w@cEoC~@y@cEkAb@??oJnDIIe@oBcA^m@uC`@Q??`@MHQg@}BaA^e@kC]N@H_@NUKw@^Cn@P|@d@QnChM}FbCrAhGn@xByEfB??o@TvCnNdDmATdAjA[AW`AI??z@IZOFZvB{@dBhIh@Ow@mDd@O")
	private String geometry;

	@Schema(description = "기록 제목", example = "오운완~^^")
	private String title;
}
