package com.ssafy.a401.artwalk_backend.domain.route.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

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
@Schema(description = "경로 요청 DTO")
public class RouteRequestDTO {
	@Schema(description = "소요 시간 (단위: 초)", nullable = false, example = "231.12")
	private double duration;

	@Schema(description = "거리 (단위: m)", nullable = false, example = "423.23")
	private double distance;

	@Schema(description = "polyline 인코딩된 경로", example = "}e{cFemjfWaA}EdEgBh@yAw@yDlPeG??SF{@_EuAf@w@cEoC~@y@cEkAb@??oJnDIIe@oBcA^m@uC`@Q??`@MHQg@}BaA^e@kC]N@H_@NUKw@^Cn@P|@d@QnChM}FbCrAhGn@xByEfB??o@TvCnNdDmATdAjA[AW`AI??z@IZOFZvB{@dBhIh@Ow@mDd@O")
	private String geometry;

	@Schema(description = "경로 제목", example = "나의 멋진 경로")
	private String title;
}
