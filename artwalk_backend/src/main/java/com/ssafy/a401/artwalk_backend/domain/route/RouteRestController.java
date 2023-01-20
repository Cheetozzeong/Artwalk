package com.ssafy.a401.artwalk_backend.domain.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.RowVersionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("route")
public class RouteRestController {
	@Autowired
	private  RouteService routeService;

	@PostMapping("")
	public Map<String, Object> routeAdd(@RequestBody Map<String, Object> request){
		Map<String, Object> response = new HashMap<>();

		Route route = new Route();
		route.setDuration((double)request.get("duration"));
		route.setDistance((double)request.get("distance"));
		route.setTitle((String)request.get("title"));

		Route result = routeService.addRoute(route, (List<List<Double>>)request.get("coordinates"));
		if(result != null){
			response.put("code", 200);
			response.put("description", "경로 저장 성공");
		}else{
			response.put("code", 500);
			response.put("description", "경로 저장 실패");
		}

		return response;
	}

	@GetMapping("/list")
	public Map<String, Object> routeList(){
		Map<String, Object> response = new HashMap<>();

		List<Map<String, Object>> routes = routeService.findAllRoute();

		if(routes != null){
			response.put("code", 200);
			response.put("description", "경로 목록 호출 성공");
			response.put("routes", routes);
		}else{
			response.put("code", 500);
			response.put("description", "경로 목록 호출 실패");
		}

		return response;
	}

	@GetMapping("/list/{userId}")
	public Map<String, Object> routeListByUserId(@PathVariable("userId") String userId){
		Map<String, Object> response = new HashMap<>();

		List<Map<String, Object>> routes = routeService.findByUserId(userId);

		if(routes != null){
			response.put("code", 200);
			response.put("description", "경로 목록 호출 성공");
			response.put("routes", routes);
		}else{
			response.put("code", 500);
			response.put("description", "경로 목록 호출 실패");
		}

		return response;
	}


	@GetMapping("/{routeId}")
	public Map<String, Object> routeDetails(@PathVariable("routeId") int routeId){
		Map<String, Object> response = new HashMap<>();

		Map<String, Object> map = routeService.findByRouteId(routeId);
		Route route = (Route)map.get("route");

		if(route != null){
			response.put("code", 200);
			response.put("description", "경로 목록 호출 성공");
			response.put("userId", route.getUserId());
			response.put("maker", route.getMaker());
			response.put("duration", route.getDuration());
			response.put("distance", route.getDistance());
			response.put("creation", route.getCreation());
			response.put("title", route.getTitle());
			response.put("coordinates", routeService.decode((String)map.get("encodedRoute")));
		}else{
			response.put("code", 500);
			response.put("description", "경로 목록 호출 실패");
		}

		return response;
	}

	@PutMapping("/{routeId}")
	public Map<String, Object> routeModify(@PathVariable("routeId") int routeId, @RequestBody Map<String, Object> request){
		Map<String, Object> response = new HashMap<>();

		Map<String, Object> map = routeService.findByRouteId(routeId);
		Route route = (Route)map.get("route");
		route.setDuration((double)request.get("duration"));
		route.setDistance((double)request.get("distance"));
		route.setTitle((String)request.get("title"));

		Route result = routeService.modifyRoute(route, (List<List<Double>>)request.get("coordinates"));
		if(result != null){
			response.put("code", 200);
			response.put("description", "경로 저장 성공");
		}else{
			response.put("code", 500);
			response.put("description", "경로 저장 실패");
		}

		return response;
	}

	@DeleteMapping("/{routeId}")
	public Map<String, Object> routeRemove(@PathVariable("routeId") int routeId){
		Map<String, Object> response = new HashMap<>();

		Map<String, Object> map = routeService.findByRouteId(routeId);
		int result = routeService.removeRoute((Route)map.get("route"));
		if(result == 0){
			response.put("code", 200);
			response.put("description", "경로 삭제 성공");
		}else{
			response.put("code", 500);
			response.put("description", "경로 삭제 실패");
		}

		return response;
	}

	@GetMapping("/count")
	public Map<String, Object> routeCount(){
		Map<String, Object> response = new HashMap<>();

		long count = routeService.findRouteCount();
		response.put("count", count);

		return response;
	}
}
