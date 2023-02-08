package com.ssafy.a401.artwalk_backend.domain.route.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiImplicitParams;
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

import com.ssafy.a401.artwalk_backend.domain.admin.model.AdminDTO;
import com.ssafy.a401.artwalk_backend.domain.admin.service.AdminService;
import com.ssafy.a401.artwalk_backend.domain.common.model.ResponseDTO;
import com.ssafy.a401.artwalk_backend.domain.route.service.RouteService;
import com.ssafy.a401.artwalk_backend.domain.route.model.Route;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"경로 API"}, description = "경로 정보 API 입니다.")
@RestController
@RequestMapping("route")
@RequiredArgsConstructor
public class RouteRestController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";

	private final RouteService routeService;
	private final AdminService adminService;

	@Operation(summary = "경로 저장", description = "경로 저장 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 저장 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 저장 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PostMapping("")
	public ResponseDTO routeAdd(@RequestBody Route route, @ApiIgnore Authentication authentication){
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
	@ApiImplicitParam(name = "user", value = "true: accessToken과 일치하는 사용자의 경로 목록을 반환합니다. ||  false: 모든 사용자의 경로 목록을 반환합니다.", dataType = "boolean")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 목록 조회 성공"),
		@ApiResponse(responseCode = FAIL, description = "경로 목록 조회 실패")
	})
	@GetMapping("/list")
	public Map<String, Object> routeList(@RequestParam(name="user") boolean userOption, @ApiIgnore Authentication authentication) {
		Map<String, Object> response = new HashMap<>();
		List<Route> routes = null;

		if(userOption) {
			String userId = authentication.getName();
			routes = routeService.findByUserId(userId);
		}else {
			routes = routeService.findAllRoute();
		}

		if(routes != null) {
			response.put("code", OK);
			response.put("routes", routes);
		}else {
			response.put("code", FAIL);
			response.put("routes", null);
		}

		return response;
	}

	@Operation(summary = "경로 검색", description = "경로 검색 메서드입니다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "경로 검색 옵션", dataType = "String"),
			@ApiImplicitParam(name = "keyword", value = "경로 검색 키워드", dataType = "String")
	})
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "경로 목록 조회 성공"),
			@ApiResponse(responseCode = FAIL, description = "경로 목록 조회 실패")
	})
	@GetMapping("/search")
	public ResponseDTO routeListSearch(@RequestParam(name = "type") String type, @RequestParam(name = "keyword") String keyword) {
		ResponseDTO response = null;
		List<Route> routes = null;

		if(type.equals("userId")) {
			String userId = keyword;
			routes = routeService.findByUserIdContaining(userId);
		} else if (type.equals("maker")) {
			String maker = keyword;
			routes = routeService.findByMakerContaining(maker);
		} else if (type.equals("title")) {
			String title = keyword;
			routes = routeService.findByTitleContaining(title);
		}

		if(routes != null) {
			response = new ResponseDTO(OK, routes);
		}else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "관리자용 사용자 경로 목록 조회", description = "관리자용 특정 사용자 경로 목록 조회 메서드입니다.")
	@ApiImplicitParam(name = "userId", value = "경로 목록을 조회할 사용자 ID (예시. ssafy@ssafy.com)", dataType = "String")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "경로 목록 조회 성공"),
			@ApiResponse(responseCode = FAIL, description = "경로 목록 조회 실패")
	})
	@GetMapping("/list/{userId}")
	public ResponseDTO routeListByUserId(@PathVariable("userId") String userId){
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

	@Operation(summary = "관리자용 사용자 경로 삭제", description = "관리자용 사용자 경로 삭제 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "관리자용 사용자 경로 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "관리자용 사용자 경로 삭제 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@DeleteMapping("/admin/{routeId}")
	public ResponseDTO routeRemoveAdmin(@RequestBody AdminDTO adminDTO, @PathVariable("routeId") int routeId, Authentication authentication) {
		ResponseDTO response = null;

		Route route = routeService.findByRouteId(routeId);
		adminDTO.setUserId(authentication.getName());
		int result = adminService.checkPassword(adminDTO);

		if (result == 0) {
			int res = routeService.removeRoute(route);
			if (res == 0) {
				response = new ResponseDTO(OK, result);
			} else {
				response = new ResponseDTO(FAIL, result);
			}
		} else {
			response = new ResponseDTO(FAIL, result);
		}
		return response;
	}

	@Operation(summary = "경로 조회", description = "경로 조회 메서드입니다.")
	@ApiImplicitParam(name = "routeId", value = "조회할 경로 Id", dataType = "int")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/{routeId}")
	public ResponseDTO routeDetails(@PathVariable("routeId") int routeId) {
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
	@ApiImplicitParam(name = "routeId", value = "수정할 경로 Id", dataType = "int")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 수정 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 수정 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PutMapping("/{routeId}")
	public ResponseDTO routeModify(@PathVariable("routeId") int routeId, @RequestBody Route route, @ApiIgnore Authentication authentication) {
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
	@ApiImplicitParam(name = "routeId", value = "삭제할 경로 Id", dataType = "int")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "경로 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "경로 삭제 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@DeleteMapping("/{routeId}")
	public ResponseDTO routeRemove(@PathVariable("routeId") int routeId) {
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
	@ApiImplicitParam(name = "routeId", value = "조회할 경로 Id", dataType = "int")
	@GetMapping("/thumb/{routeId}")
	public ResponseEntity<Resource> displayRouteThumbnail(@PathVariable("routeId") int routeId) {
		Route route = routeService.findByRouteId(routeId);
		ResponseEntity<Resource> response = routeService.getThumbnailImage(route);
		return response;
	}
}
