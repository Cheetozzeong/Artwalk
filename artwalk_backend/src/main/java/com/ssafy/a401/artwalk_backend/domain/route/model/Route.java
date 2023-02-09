package com.ssafy.a401.artwalk_backend.domain.route.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

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
@Entity
@DynamicInsert
@Table(name = "route")
@Schema(description = "경로 Entity")
public class Route {
	@Id
	@Column(nullable = false)
	@Schema(description = "경로 ID", nullable = false, example = "6")
	private int routeId;

	@Column(nullable = false, length = 50)
	@Schema(description = "사용자 ID", nullable = false, example = "ssafy@ssafy.com")
	private String userId;

	@Column(nullable = false, length = 50)
	@Schema(description = "작성자 ID", nullable = false, example = "ssafy@ssafy.com")
	private String maker;

	@Column(nullable = false)
	@Schema(description = "소요 시간", nullable = false, example = "231.12")
	private double duration;

	@Column(nullable = false)
	@Schema(description = "거리", nullable = false, example = "423.23")
	private double distance;

	@Column(columnDefinition = "default NULL")
	@Schema(description = "polyline 인코딩된 경로", example = "}e{cFemjfWaA}EdEgBh@yAw@yDlPeG??SF{@_EuAf@w@cEoC~@y@cEkAb@??oJnDIIe@oBcA^m@uC`@Q??`@MHQg@}BaA^e@kC]N@H_@NUKw@^Cn@P|@d@QnChM}FbCrAhGn@xByEfB??o@TvCnNdDmATdAjA[AW`AI??z@IZOFZvB{@dBhIh@Ow@mDd@O")
	private String geometry;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	// @Schema(description = "작성 시간", nullable = false, example = "2023-01-25T04:44:46.761Z")
	private Date creation;

	@Column(length = 255, columnDefinition = "default NULL")
	@Schema(description = "경로 썸네일 요청 경로", example = "/route/thumb/6")
	private String thumbnail;

	@Column(length = 16, columnDefinition = "default '나의 경로'")
	@Schema(description = "경로 제목", example = "나의 멋진 경로")
	private String title;
}
