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

import com.ssafy.a401.artwalk_backend.domain.common.ResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
		@ApiResponse(responseCode = "Ok", description = "경로 저장 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = "Fail", description = "경로 저장 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PostMapping("")
	public ResponseDTO routeAdd(@RequestBody Route route){
		ResponseDTO response = null;

		Route result = routeService.addRoute(route);
		if(result != null){
			response = new ResponseDTO("Ok", result);
		}else{
			response = new ResponseDTO("Fail", null);
		}

		return response;
	}

	@Operation(summary = "모든 경로 목록 조회", description = "모든 경로 목록 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "Ok", description = "경로 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = "Fail", description = "경로 목록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/list")
	public ResponseDTO routeList(){
		ResponseDTO response = null;

		List<Route> routes = routeService.findAllRoute();
		if(routes != null){
			response = new ResponseDTO("Ok", routes);
		}else{
			response = new ResponseDTO("Fail", null);
		}

		return response;
	}

	@Operation(summary = "사용자 경로 목록 조회", description = "특정 사용자 경로 목록 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "Ok", description = "경로 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = "Fail", description = "경로 목록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/list/{userId}")
	public ResponseDTO routeListByUserId(@Parameter(name = "userId", description = "사용자 ID") @PathVariable("userId") String userId){
		ResponseDTO response = null;

		List<Route> routes = routeService.findByUserId(userId);
		if(routes != null){
			response = new ResponseDTO("Ok", routes);
		}else{
			response = new ResponseDTO("Fail", null);
		}

		return response;
	}

	@Operation(summary = "경로 조회", description = "경로 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "Ok", description = "경로 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = "Fail", description = "경로 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/{routeId}")
	public ResponseDTO routeDetails(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId){
		ResponseDTO response = null;

		Route route = routeService.findByRouteId(routeId);
		if(route != null){
			route.setThumbnail(routeService.makeThumbnailUrl(route.getRouteId()));
			route.setGeometry(routeService.readFile(route.getGeometry()));
			response = new ResponseDTO("Ok", route);
		}else{
			response = new ResponseDTO("Fail", null);
		}

		return response;
	}

	@Operation(summary = "경로 수정", description = "경로 수정 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "Ok", description = "경로 수정 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = "Fail", description = "경로 수정 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PutMapping("/{routeId}")
	public ResponseDTO routeModify(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId, @RequestBody Route route){
		ResponseDTO response = null;

		Route originRoute  = routeService.findByRouteId(routeId);
		Route result = routeService.modifyRoute(originRoute, route);
		if(result != null){
			response = new ResponseDTO("Ok", result);
		}else{
			response = new ResponseDTO("Fail", null);
		}

		return response;
	}

	@Operation(summary = "경로 삭제", description = "경로 삭제 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "Ok", description = "경로 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = "Fail", description = "경로 삭제 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@DeleteMapping("/{routeId}")
	public ResponseDTO routeRemove(@Parameter(name = "routeId", description = "경로 ID") @PathVariable("routeId") int routeId){
		ResponseDTO response = null;

		Route route = routeService.findByRouteId(routeId);
		int result = routeService.removeRoute(route);
		if(result == 0){
			response = new ResponseDTO("Ok", result);
		}else{
			response = new ResponseDTO("Fail", result);
		}

		return response;
	}

	@Operation(summary = "경로 개수 조회", description = "경로 개수 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "Ok", description = "경로 개수 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/count")
	public ResponseDTO routeCount(){
		long count = routeService.findRouteCount();
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		ResponseDTO response = new ResponseDTO("Ok", map);
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
