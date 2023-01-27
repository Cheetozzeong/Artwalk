package com.ssafy.a401.artwalk_backend.domain.record.model;

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
@Entity
@Table(name = "record")
@Schema(description = "기록 DTO")
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	@Schema(description = "기록 ID", nullable = false, example = "17")
	private int recordId;

	@Column(nullable = false, length = 50)
	@Schema(description = "사용자 ID", nullable = false, example = "ssafy@ssafy.com")
	private String userId;

	@Column(nullable = false)
	@Schema(description = "소요 시간", nullable = false, example = "231.12")
	private double taken;

	@Column(nullable = false)
	@Schema(description = "거리", nullable = false, example = "423.23")
	private double distance;

	@Column(columnDefinition = "default NULL")
	@Schema(description = "인코딩된 기록", example = "go%7bcFqkkfWoDaQEw%40UKw%40%5eI_%40_%40XqQdGyc%40l%60%40yf%40%7eA_%60%40pM%7dtCaTa%40_%40%3f%40yUzEUOMDePaU%7dVVjBmHMUBc%40Q%3f")
	private String geometry;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Schema(description = "작성 시간", nullable = false, example = "2023-01-25T04:44:46.761Z")
	private Date creation;

	@Column(length = 255, columnDefinition = "default NULL")
	@Schema(description = "기록 썸네일 요청 경로", example = "/record/thumb/6")
	private String thumbnail;

	@Column(length = 255, columnDefinition = "default NULL")
	@Schema(description = "공유이미지 요청 경로", example = "/record/share/6")
	private String recentImage;

	@Column(length = 40, columnDefinition = "default NULL")
	@Schema(description = "기록 메모", example = "오운완~^^")
	private String detail;
}
