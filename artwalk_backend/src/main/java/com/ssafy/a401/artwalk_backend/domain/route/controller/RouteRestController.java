package com.ssafy.a401.artwalk_backend.domain.route.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a401.artwalk_backend.domain.common.model.ResponseDTO;
import com.ssafy.a401.artwalk_backend.domain.route.service.RouteService;
import com.ssafy.a401.artwalk_backend.domain.route.model.Route;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// @Tag(name = "경로", description = "Artwalk 경로 API 입니다.")
@RestController
@RequestMapping("route")
public class RouteRestController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";

	@Autowired
	private RouteService routeService;

	@Operation(summary = "경로 저장", description = "경로 저장 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 저장 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 저장 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PostMapping("")
	public ResponseDTO routeAdd(@RequestBody Route route, Authentication authentication){
		ResponseDTO response = null;

		String userId = authentication.getName();
		Route result = routeService.addRoute(route, userId);
		if(result != null){
			response = new ResponseDTO(OK, result);
		}else{
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "경로 목록 조회", description = "경로 목록 조회 메서드입니다. user가 true면 특정 사용자의 경로 목록만 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 목록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/list")
	public ResponseDTO routeList(@RequestParam(name="user") boolean userOption, Authentication authentication) {
		ResponseDTO response = null;
		List<Route> routes = null;

		if(userOption) {
			String userId = authentication.getName();
			routes = routeService.findByUserId(userId);
		}else {
			routes = routeService.findAllRoute();
		}

		if(routes != null) {
			response = new ResponseDTO(OK, routes);
		}else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "관리자용 사용자 경로 목록 조회", description = "관리자용 특정 사용자 경로 목록 조회 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "경로 목록 조회 성공"),
			@ApiResponse(responseCode = FAIL, description = "경로 목록 조회 실패")
	})
	@GetMapping("/list/{userId}")
	public ResponseDTO routeListByUserId(@Parameter(name = "userId", description = "사용자 ID") @PathVariable("userId") String userId){
		ResponseDTO response = null;
		List<Route> routes = null;

		routes = routeService.findByUserId(userId);

		if(routes != null) {
			response = new ResponseDTO(OK, routes);
		}else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "경로 조회", description = "경로 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/{routeId}")
	public ResponseDTO routeDetails(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId) {
		ResponseDTO response = null;

		Route route = routeService.findByRouteId(routeId);
		if(route != null) {
			route.setThumbnail(routeService.makeThumbnailUrl(route.getRouteId()));
			route.setGeometry(routeService.readGeometryFile(route));
			response = new ResponseDTO(OK, route);
		}else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "경로 수정", description = "경로 수정 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 수정 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 수정 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PutMapping("/{routeId}")
	public ResponseDTO routeModify(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId, @RequestBody Route route, Authentication authentication) {
		ResponseDTO response = null;

		String userId = authentication.getName();
		Route originRoute  = routeService.findByRouteId(routeId);
		Route result = routeService.modifyRoute(originRoute, route, userId);
		if(result != null) {
			response = new ResponseDTO(OK, result);
		}else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "경로 삭제", description = "경로 삭제 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 삭제 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@DeleteMapping("/{routeId}")
	public ResponseDTO routeRemove(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId) {
		ResponseDTO response = null;

		Route route = routeService.findByRouteId(routeId);
		int result = routeService.removeRoute(route);
		if(result == 0) {
			response = new ResponseDTO(OK, result);
		}else {
			response = new ResponseDTO(FAIL, result);
		}

		return response;
	}

	@Operation(summary = "경로 개수 조회", description = "경로 개수 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 개수 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/count")
	public ResponseDTO routeCount() {
		long count = routeService.getRouteCount();
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		ResponseDTO response = new ResponseDTO(OK, map);
		return response;
	}

	@Operation(summary = "경로 썸네일 조회", description = "경로 썸네일 조회 메서드입니다.")
	@GetMapping("/thumb/{routeId}")
	public ResponseEntity<Resource> displayRouteThumbnail(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId) {
		Route route = routeService.findByRouteId(routeId);
		ResponseEntity<Resource> response = routeService.getThumbnailImage(route);
		return response;
	}
}
