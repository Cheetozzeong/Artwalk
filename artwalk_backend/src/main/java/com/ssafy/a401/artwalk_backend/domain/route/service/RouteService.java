package com.ssafy.a401.artwalk_backend.domain.route.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.a401.artwalk_backend.domain.common.service.FileService;
import com.ssafy.a401.artwalk_backend.domain.route.model.Route;
import com.ssafy.a401.artwalk_backend.domain.route.repository.RouteRepository;

@Service
public class RouteService {
	@Autowired
	private RouteRepository routeRepository;
	private FileService fileService = new FileService("route");

	public Route addRoute(Route route, String userId) {
		Route result = null;

		route.setUserId(userId);
		route.setMaker(userId); // TODO: 경로 사용자/최초생성자 관련 기능 추후 추가 예정

		String geometryPath = fileService.saveFile(route.getGeometry(), route.getUserId());
		route.setGeometry(geometryPath);

		String thumbPath = fileService.saveThumbnail(geometryPath, fileService.readFile(route.getGeometry(), route.getUserId()), route.getUserId());
		route.setThumbnail(thumbPath);

		result = routeRepository.save(route);

		return result;
	}

	public Route modifyRoute(Route originRoute, Route newRoute, String userId) {
		Route result = null;

		originRoute.setUserId(userId);

		String updateGeometry = newRoute.getGeometry();
		String geometryPath = fileService.saveFile(updateGeometry, originRoute.getUserId());
		fileService.removeFile(originRoute.getGeometry(), originRoute.getUserId());
		originRoute.setGeometry(geometryPath);

		String thumbPath = fileService.saveThumbnail(geometryPath, updateGeometry, originRoute.getUserId());
		fileService.removeFile(originRoute.getThumbnail(), originRoute.getUserId());
		originRoute.setThumbnail(thumbPath);

		result = routeRepository.save(originRoute);

		return result;
	}

	public int removeRoute(Route route) {
		routeRepository.delete(route);
		fileService.removeFile(route.getGeometry(), route.getUserId());
		fileService.removeFile(route.getThumbnail(), route.getUserId());
		int result = routeRepository.countByRouteId(route.getRouteId());
		return result;
	}

	/** 저장된 경로의 개수를 반환합니다. */
	public long getRouteCount() {
		long count = routeRepository.count();
		return count;
	}

	/** 저장된 모든 경로를 반환합니다. */
	public List<Route> findAllRoute() {
		List<Route> routeList = new ArrayList<>();
		List<Route> routes = routeRepository.findAll();
		for (Route route : routes) {
			route.setThumbnail(makeThumbnailUrl(route.getRouteId()));
			route.setGeometry(fileService.readFile(route.getGeometry(), route.getUserId()));
			routeList.add(route);
		}
		return routeList;
	}

	/** 저장된 경로 중 route_id가 일치하는 경로를 반환합니다. */
	public Route findByRouteId(int routeId) {
		Route route = routeRepository.findById(routeId).get();
		return route;
	}

	/** 저장된 경로 중 user_id가 일치하는 경로를 반환합니다. */
	public List<Route> findByUserId(String userId) {
		List<Route> routeList = new ArrayList<>();
		List<Route> routes = routeRepository.findByUserId(userId);
		for (Route route : routes) {
			route.setThumbnail(makeThumbnailUrl(route.getRouteId()));
			route.setGeometry(fileService.readFile(route.getGeometry(), route.getUserId()));
			routeList.add(route);
		}
		return routeList;
	}

	public String readGeometryFile(Route route) {
		return fileService.readFile(route.getGeometry(), route.getUserId());
	}

	public ResponseEntity<Resource> getThumbnailImage(Route route) {
		return fileService.findThumbnail(route.getThumbnail(), route.getUserId());
	}

	/** 썸네일 요청 경로를 반환합니다. */
	public static String makeThumbnailUrl(int routeId) {
		String thumbUrl = "/route/thumb/" + routeId;
		return thumbUrl;
	}
}
