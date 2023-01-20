package com.ssafy.a401.artwalk_backend.domain.route;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

@Service
public class RouteService {
	@Autowired
	private  RouteRepository routeRepository;
	
	// TODO: 임시 파일 경로
	private static final String FILE_PATH = "C:/artwalk_resource/";

	/** 경로(route) 좌표 배열을 인코딩하여 문자열로 변환합니다. */
	public static String encode(List<List<Double>> coordinates){
		List<LatLng> coordList = new ArrayList<>();
		for (List<Double> lngLat : coordinates){
			double lng = lngLat.get(0);
			double lat = lngLat.get(1);
			coordList.add(new LatLng(lat, lng));
		}

		String encodedRoute = PolylineEncoding.encode(coordList);
		return encodedRoute;
	}

	/** 인코딩된 경로(route) 문자열을 디코딩하여 좌표 배열로 변환합니다. */
	public static List<List<Double>> decode(String encodedRoute) {
		List<List<Double>> coordinates = new ArrayList<>();

		List<LatLng> coordList = PolylineEncoding.decode(encodedRoute);
		for (LatLng latLng : coordList) {
			coordinates.add(new ArrayList<>(Arrays.asList(latLng.lng, latLng.lat)));
		}

		return coordinates;
	}

	/** 경로를 저장합니다. */
	public Route addRoute(Route route, List<List<Double>> coordinates){
		Route result = null;

		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		String saveFolder = today;
		File folder = new File(saveFolder);
		if (!folder.exists()){ // 경로 확인
			folder.mkdirs();
		}

		String time = Long.toString(System.currentTimeMillis());
		String rand = Integer.toString((int)(Math.random()*1000000));
		String saveFileName = time+rand+".txt";

		String encodedRoute = encode(coordinates);

		try {
			String saveFolderPath = folder + File.separator + saveFileName;
			BufferedWriter fw = new BufferedWriter(new FileWriter(FILE_PATH + saveFolderPath, true));
			fw.write(encodedRoute);
			fw.flush();
			fw.close();

			route.setRoute(saveFolderPath);

			// TODO: 로그인 아이디 획득 및 저장하는 기능 추가
			route.setUserId("ssafy"); // 임시로 계정 정보 등록
			// TODO: 생성자 아이디 획득 및 저장하는 기능 추가
			route.setMaker("ssafy"); // 임시로 등록자 정보 등록
			// TODO: 썸네일 파일 생성 기능 추가
			// TODO: 썸네일 파일 저장 기능 추가

			result = routeRepository.save(route);
		}catch (Exception e){
			e.printStackTrace();
		}

		return result;
	}

	/** 경로를 수정합니다. */
	public Route modifyRoute(Route route, List<List<Double>> coordinates){
		Route result = null;

		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		String saveFolder = today;
		File folder = new File(saveFolder);
		if (!folder.exists()){ // 경로 확인
			folder.mkdirs();
		}

		String time = Long.toString(System.currentTimeMillis());
		String rand = Integer.toString((int)(Math.random()*1000000));
		String saveFileName = time+rand+".txt";

		String encodedRoute = encode(coordinates);

		try {
			String saveFolderPath = folder + File.separator + saveFileName;
			BufferedWriter fw = new BufferedWriter(new FileWriter(FILE_PATH + saveFolderPath, true));
			fw.write(encodedRoute);
			fw.flush();
			fw.close();

			Path originalFilePath = Paths.get(FILE_PATH + route.getRoute());
			try {
				Files.delete(originalFilePath);
			} catch (NoSuchFileException e) {
				System.out.println("삭제하려는 파일이 없습니다");
			} catch (IOException e) {
				e.printStackTrace();
			}

			route.setRoute(saveFolderPath);

			// TODO: 로그인 아이디 획득 및 저장하는 기능 추가
			route.setUserId("ssafy"); // 임시로 계정 정보 등록
			// TODO: 썸네일 파일 생성 기능 추가
			// TODO: 썸네일 파일 저장 기능 추가

			result = routeRepository.save(route);
		}catch (Exception e){
			e.printStackTrace();
		}

		return result;
	}

	/** 경로를 삭제합니다. */
	public int removeRoute(Route route){
		Path originalFilePath = Paths.get(FILE_PATH + route.getRoute());
		try {
			Files.delete(originalFilePath);
		} catch (NoSuchFileException e) {
			System.out.println("삭제하려는 파일이 없습니다");
		} catch (IOException e) {
			e.printStackTrace();
		}

		routeRepository.delete(route);
		int result = routeRepository.countByRouteId(route.getRouteId());
		return result;
	}

	/** 저장된 경로의 개수를 반환합니다. */
	public long findRouteCount(){
		long count = routeRepository.count();
		return count;
	}

	/** 저장된 모든 경로를 반환합니다. */
	public List<Map<String, Object>> findAllRoute(){
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> routeList = new ArrayList<>();

		List<Route> routes = routeRepository.findAll();
		for (Route route : routes){
			Map<String, Object> map = objectMapper.convertValue(route, Map.class);
			map.remove("duration");
			map.remove("distance");
			map.remove("route");
			routeList.add(map);
		}

		return routeList;
	}

	/** 저장된 경로 중 route_id가 일치하는 경로를 반환합니다. */
	public Map<String, Object> findByRouteId(int routeId){
		Route route = routeRepository.findById(routeId).get();
		String encodedRoute = "";

		File file = new File(route.getRoute());
		if(file.exists()){ // 파일이 존재하는 경우
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				encodedRoute = in.readLine();
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		Map<String, Object> map = new HashMap<>();
		map.put("route", route);
		map.put("encodedRoute", encodedRoute);

		return map;
	}

	/** 저장된 경로 중 user_id가 일치하는 경로를 반환합니다. */
	public List<Map<String, Object>> findByUserId(String userId){
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> routeList = new ArrayList<>();

		List<Route> routes = routeRepository.findByUserId(userId);
		for (Route route : routes){
			Map<String, Object> map = objectMapper.convertValue(route, Map.class);
			map.remove("duration");
			map.remove("distance");
			map.remove("route");
			routeList.add(map);
		}

		return routeList;
	}
}
