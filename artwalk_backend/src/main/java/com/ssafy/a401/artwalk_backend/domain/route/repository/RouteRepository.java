package com.ssafy.a401.artwalk_backend.domain.route.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a401.artwalk_backend.domain.route.model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
	long countByRouteId(int routeId);

	long countByUserId(String userId);

	List<Route> findByUserIdOrderByRouteIdDesc(String userId);

	List<Route> findByUserIdContainingOrderByRouteIdDesc(String userId);

	List<Route> findByMakerContainingOrderByRouteIdDesc(String maker);

	List<Route> findByTitleContainingOrderByRouteIdDesc(String title);
}
