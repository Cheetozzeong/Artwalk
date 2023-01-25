package com.ssafy.a401.artwalk_backend.domain.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "경로", description = "Artwalk 경로 API 입니다.")
@RestController
@RequestMapping("route")
public class RouteRestController {
	@Autowired
	private  RouteService routeService;

	@Operation(summary = "경로 저장", description = "경로 저장 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경로 저장 성공"),
		@ApiResponse(responseCode = "400", description = "경로 저장 실패")
	})
	@PostMapping("")
	public Map<String, Object> routeAdd(@RequestBody Route route){
		Map<String, Object> response = new HashMap<>();

		Route result = routeService.addRoute(route);
		if(result != null){
			response.put("code", 200);
			response.put("description", "경로 저장 성공");
		}else{
			response.put("code", 400);
			response.put("description", "경로 저장 실패");
		}

		return response;
	}

	@Operation(summary = "모든 경로 목록 조회", description = "모든 경로 목록 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경로 목록 조회 성공"),
		@ApiResponse(responseCode = "400", description = "경로 목록 조회 실패")
	})
	@GetMapping("/list")
	public Map<String, Object> routeList(){
		Map<String, Object> response = new HashMap<>();

		List<Map<String, Object>> routes = routeService.findAllRoute();

		if(routes != null){
			response.put("code", 200);
			response.put("description", "경로 목록 조회 성공");
			response.put("routes", routes);
		}else{
			response.put("code", 400);
			response.put("description", "경로 목록 조회 실패");
		}

		return response;
	}

	@Operation(summary = "사용자 경로 목록 조회", description = "특정 사용자 경로 목록 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경로 목록 조회 성공"),
		@ApiResponse(responseCode = "400", description = "경로 목록 조회 실패")
	})
	@GetMapping("/list/{userId}")
	public Map<String, Object> routeListByUserId(@Parameter(name = "userId", description = "사용자 ID") @PathVariable("userId") String userId){
		Map<String, Object> response = new HashMap<>();

		List<Map<String, Object>> routes = routeService.findByUserId(userId);

		if(routes != null){
			response.put("code", 200);
			response.put("description", "경로 목록 조회 성공");
			response.put("routes", routes);
		}else{
			response.put("code", 400);
			response.put("description", "경로 목록 조회 실패");
		}

		return response;
	}

	@Operation(summary = "경로 조회", description = "경로 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경로 조회 성공"),
		@ApiResponse(responseCode = "400", description = "경로 조회 실패")
	})
	@GetMapping("/{routeId}")
	public Map<String, Object> routeDetails(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId){
		Map<String, Object> response = new HashMap<>();

		Route route = routeService.findByRouteId(routeId);

		if(route != null){
			response.put("code", 200);
			response.put("description", "경로 조회 성공");
			response.put("userId", route.getUserId());
			response.put("maker", route.getMaker());
			response.put("duration", route.getDuration());
			response.put("distance", route.getDistance());
			response.put("creation", route.getCreation());
			response.put("title", route.getTitle());
			response.put("geometry", routeService.readFile(route.getGeometry()));
		}else{
			response.put("code", 400);
			response.put("description", "경로 조회 실패");
		}

		return response;
	}

	@Operation(summary = "경로 수정", description = "경로 수정 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경로 수정 성공"),
		@ApiResponse(responseCode = "400", description = "경로 수정 실패")
	})
	@PutMapping("/{routeId}")
	public Map<String, Object> routeModify(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId, @RequestBody Route route){
		Map<String, Object> response = new HashMap<>();

		Route originRoute  = routeService.findByRouteId(routeId);
		Route result = routeService.modifyRoute(originRoute, route);
		if(result != null){
			response.put("code", 200);
			response.put("description", "경로 수정 성공");
		}else{
			response.put("code", 400);
			response.put("description", "경로 수정 실패");
		}

		return response;
	}

	@Operation(summary = "경로 삭제", description = "경로 삭제 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경로 삭제 성공"),
		@ApiResponse(responseCode = "400", description = "경로 삭제 실패")
	})
	@DeleteMapping("/{routeId}")
	public Map<String, Object> routeRemove(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId){
		Map<String, Object> response = new HashMap<>();

		Route route = routeService.findByRouteId(routeId);
		int result = routeService.removeRoute(route);
		if(result == 0){
			response.put("code", 200);
			response.put("description", "경로 삭제 성공");
		}else{
			response.put("code", 400);
			response.put("description", "경로 삭제 실패");
		}

		return response;
	}

	@Operation(summary = "경로 개수 조회", description = "경로 개수 조회 메서드입니다.")
	@GetMapping("/count")
	public Map<String, Object> routeCount(){
		Map<String, Object> response = new HashMap<>();

		long count = routeService.findRouteCount();
		response.put("count", count);

		return response;
	}

	@Operation(summary = "경로 썸네일 조회", description = "경로 썸네일 조회 메서드입니다.")
	@GetMapping("/thumb/{routeId}")
	public ResponseEntity<Resource> displayRouteThumbnail(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId) {
		Route route = routeService.findByRouteId(routeId);
		ResponseEntity<Resource> response = routeService.findThumbnail(route.getThumbnail());
		return response;
	}
}
