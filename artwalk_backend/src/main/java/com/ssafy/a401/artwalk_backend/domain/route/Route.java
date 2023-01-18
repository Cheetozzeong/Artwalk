package com.ssafy.a401.artwalk_backend.domain.route;

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
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private int routeId;

	@Column(nullable = false, length = 50)
	private String userId;

	@Column(nullable = false, length = 50)
	private String maker;

	@Column(nullable = false)
	private double duration;

	@Column(nullable = false)
	private double distance;

	@Column(length = 255, columnDefinition = "default NULL")
	private String route;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date creation;

	@Column(length = 255, columnDefinition = "default NULL")
	private String thumbnail;

	@Column(length = 16, columnDefinition = "default 나의 경로'")
	private String title;
}
