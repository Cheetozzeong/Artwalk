package com.ssafy.a401.artwalk_backend.domain.route.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a401.artwalk_backend.domain.route.model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
	long countByRouteId(int routeId);

	long countByUserId(String userId);

	List<Route> findByUserId(String userId);

	List<Route> findByUserIdContaining(String userId);

	List<Route> findByMakerContaining(String maker);

	List<Route> findByTitleContaining(String title);
}
