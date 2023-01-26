package com.ssafy.a401.artwalk_backend.domain.route;

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
@Table(name = "route")
@Schema(description = "경로 DTO")
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	@Schema(description = "경로 ID", nullable = false, example = "17")
	private int routeId;

	@Column(nullable = false, length = 50)
	@Schema(description = "사용자 ID", nullable = false, example = "ssafy@ssafy.com")
	private String userId;

	@Column(nullable = false, length = 50)
	@Schema(description = "작성자 ID", nullable = false, example = "ssafy@ssafy.com")
	private String maker;

	@Column(nullable = false)
	@Schema(description = "예상 시간", nullable = false, example = "231(s)")
	private double duration;

	@Column(nullable = false)
	@Schema(description = "예상 거리", nullable = false, example = "423(m)")
	private double distance;

	@Column(columnDefinition = "default NULL")
	@Schema(description = "인코딩된 경로가 저장되는 경로", example = "230125/1234353124.txt")
	private String geometry;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Schema(description = "작성 시간", nullable = false, example = "2023-01-25T04:44:46.761Z")
	private Date creation;

	@Column(length = 255, columnDefinition = "default NULL")
	@Schema(description = "경로 썸네일이 저장되는 경로", example = "230125/1234353124.png")
	private String thumbnail;

	@Column(length = 16, columnDefinition = "default '나의 경로'")
	@Schema(description = "경로 제목", example = "나의 멋진 경로")
	private String title;
}
