package com.ssafy.a401.artwalk_backend.domain.route;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("route")
public class RouteRestController {
	@Autowired
	private  RouteRepository routeRepository;

	@GetMapping("/count")
	public Map<String, Object> routeCount(){
		Map<String, Object> response = new HashMap<>();

		long count = routeRepository.count();
		response.put("count", count);

		return response;
	}

	@PostMapping("/")
	public Map<String, Object> routeAdd(@RequestBody Route route){
		Map<String, Object> response = new HashMap<>();

		Route result = routeRepository.save(route);
		if(result != null){
			response.put("code", 200);
			response.put("description", "경로 저장 성공");
		}else{
			response.put("code", 500);
			response.put("description", "경로 저장 실패");
		}

		return response;
	}


}
