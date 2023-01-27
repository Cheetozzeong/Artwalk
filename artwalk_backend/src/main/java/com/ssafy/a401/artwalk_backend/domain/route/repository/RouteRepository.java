package com.ssafy.a401.artwalk_backend.domain.route.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a401.artwalk_backend.domain.route.model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
	int countByRouteId(int routeId);

	List<Route> findByUserId(String userId);
}
