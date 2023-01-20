package com.ssafy.a401.artwalk_backend.domain.route;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Integer> {
	int countByRouteId(int routeId);

	List<Route> findByUserId(String userId);
}
