package com.ssafy.a401.artwalk_backend.domain.route.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.a401.artwalk_backend.domain.route.model.Route;
import com.ssafy.a401.artwalk_backend.domain.route.repository.RouteRepository;

@Service
public class RouteService {
	@Autowired
	private RouteRepository routeRepository;

	private static String FILE_PATH;
	private static String MAPBOX_API_KEY;

	@Value("${file.path}")
	public void setFilePath(String filePath) {
		FILE_PATH = filePath + "route/";
	}

	@Value("${mapbox.api.key}")
	public void setMapboxApiKey(String mapboxApiKey) {
		MAPBOX_API_KEY = mapboxApiKey;
	}

	/** 경로(route) 좌표 배열을 인코딩하여 문자열로 변환합니다. */
	/* public static String encode(List<List<Double>> coordinates){
		List<LatLng> coordList = new ArrayList<>();
		for (List<Double> lngLat : coordinates){
			double lng = lngLat.get(0);
			double lat = lngLat.get(1);
			coordList.add(new LatLng(lat, lng));
		}

		String encodedRoute = PolylineEncoding.encode(coordList);
		return encodedRoute;
	} */

	/** 인코딩된 경로(route) 문자열을 디코딩하여 좌표 배열로 변환합니다. */
	/* public static List<List<Double>> decode(String encodedRoute) {
		List<List<Double>> coordinates = new ArrayList<>();

		List<LatLng> coordList = PolylineEncoding.decode(encodedRoute);
		for (LatLng latLng : coordList) {
			coordinates.add(new ArrayList<>(Arrays.asList(latLng.lng, latLng.lat)));
		}

		return coordinates;
	} */

	/** 파일 읽고 값을 반환합니다. */
	public static String readFile(String filePath, String userId){
		String result = "";
		File file = new File(FILE_PATH + userId.replace('@', '_') + "/" + filePath);
		if(file.exists()){ // 파일이 존재하는 경우
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				result = in.readLine();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

	/** 파일 저장 후 경로를 반환합니다. */
	public static String saveFile(String value, String userId){
		String saveFolderPath = "";

		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		String saveFolder = today;

		File folder = new File(FILE_PATH + userId.replace('@', '_') + "/" + saveFolder);
		if (!folder.exists()){ // 경로 확인
			folder.mkdirs();
		}

		String time = Long.toString(System.currentTimeMillis());
		String rand = Integer.toString((int)(Math.random()*1000000));
		String saveFileName = time+rand+".txt";
		try {
			saveFolderPath = saveFolder + "/" + saveFileName;
			BufferedWriter fw = new BufferedWriter(new FileWriter(FILE_PATH + userId.replace('@', '_') + "/" + saveFolderPath, true));
			fw.write(value);
			fw.flush();
			fw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return saveFolderPath;
	}

	/** 파일을 삭제합니다. */
	public static void removeFile(String filePath, String userId){
		Path path = Paths.get(FILE_PATH + userId.replace('@', '_') + "/" + filePath);
		try {
			Files.delete(path);
		} catch (NoSuchFileException e) {
			System.out.println("삭제하려는 파일이 없습니다");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 인코딩된 경로(route)를 가지고 썸네일 이미지를 생성하고 저장한 후 저장된 경로를 반환합니다. */
	public static String makeThumbnail(String geometryPath, String geometry, String userId) {
		int polyLineWidth = 5; // 경로 굵기
		String polyLineColor = "ff0000"; // 경로 색상
		int imageWidth = 400; // 이미지 가로 크기
		int imageHeight = 300; // 이미지 세로 크기

		StringBuilder imageURL = new StringBuilder();
		imageURL.append("https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/path-")
			.append(polyLineWidth)
			.append("+")
			.append(polyLineColor)
			.append("(")
			.append(geometry)
			.append(")/auto/")
			.append(imageWidth)
			.append("x")
			.append(imageHeight)
			.append("?access_token=")
			.append(MAPBOX_API_KEY);

		String filePathName = "";
		try {
			URL imgURL = new URL(imageURL.toString());
			String extension = "png";
			StringTokenizer st = new StringTokenizer(geometryPath, ".");
			filePathName = st.nextToken() + "." + extension;

			BufferedImage image = ImageIO.read(imgURL);
			File file = new File(FILE_PATH + userId.replace('@', '_') + "/" + filePathName);
			if(!file.exists()) {
				file.mkdirs();
			}

			ImageIO.write(image, extension, file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePathName;
	}

	/** 저장한 썸네일 이미지를 반환합니다. */
	public static ResponseEntity<Resource> findThumbnail(String thumbnailPath, String userId) {
		ResponseEntity<Resource> response = null;

		Resource resource = new FileSystemResource(FILE_PATH + userId.replace('@', '_') + "/" + thumbnailPath);
		if(!resource.exists()){
			response = new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try{
			filePath = Paths.get(FILE_PATH + thumbnailPath);
			header.add("Content-type", Files.probeContentType(filePath));
			response = new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	/** 썸네일 요청 경로를 반환합니다. */
	public static String makeThumbnailUrl(int routeId) {
		String thumbUrl = "/route/thumb/" + routeId;
		return thumbUrl;
	}

	/** 경로를 저장합니다. */
	public Route addRoute(Route route){
		Route result = null;

		// TODO: 로그인 아이디 획득 및 저장하는 기능 추가
		route.setUserId("2007bae@naver.com"); // 임시로 계정 정보 등록
		// TODO: 생성자 아이디 획득 및 저장하는 기능 추가
		route.setMaker("2007bae@naver.com"); // 임시로 등록자 정보 등록

		String geometryPath = saveFile(route.getGeometry(), route.getUserId());
		route.setGeometry(geometryPath);

		String thumbPath = makeThumbnail(geometryPath, readFile(route.getGeometry(), route.getUserId()), route.getUserId());
		route.setThumbnail(thumbPath);

		result = routeRepository.save(route);

		return result;
	}

	/** 경로를 수정합니다. */
	public Route modifyRoute(Route originRoute, Route newRoute){
		Route result = null;

		// TODO: 로그인 아이디 획득 및 저장하는 기능 추가
		originRoute.setUserId("2007bae@naver.com"); // 임시로 계정 정보 등록

		String updateGeometry = newRoute.getGeometry();
		String geometryPath = saveFile(updateGeometry, originRoute.getUserId());
		removeFile(originRoute.getGeometry(), originRoute.getUserId());
		originRoute.setGeometry(geometryPath);

		String thumbPath = makeThumbnail(geometryPath, updateGeometry, originRoute.getUserId());
		removeFile(originRoute.getThumbnail(), originRoute.getUserId());
		originRoute.setThumbnail(thumbPath);

		result = routeRepository.save(originRoute);

		return result;
	}

	/** 경로를 삭제합니다. */
	public int removeRoute(Route route){
		routeRepository.delete(route);
		removeFile(route.getGeometry(), route.getUserId());
		removeFile(route.getThumbnail(), route.getUserId());
		int result = routeRepository.countByRouteId(route.getRouteId());
		return result;
	}

	/** 저장된 경로의 개수를 반환합니다. */
	public long findRouteCount(){
		long count = routeRepository.count();
		return count;
	}

	/** 저장된 모든 경로를 반환합니다. */
	public List<Route> findAllRoute(){
		List<Route> routeList = new ArrayList<>();
		List<Route> routes = routeRepository.findAll();
		for (Route route : routes){
			route.setThumbnail(makeThumbnailUrl(route.getRouteId()));
			route.setGeometry(readFile(route.getGeometry(), route.getUserId()));
			routeList.add(route);
		}
		return routeList;
	}

	/** 저장된 경로 중 route_id가 일치하는 경로를 반환합니다. */
	public Route findByRouteId(int routeId){
		Route route = routeRepository.findById(routeId).get();
		return route;
	}

	/** 저장된 경로 중 user_id가 일치하는 경로를 반환합니다. */
	public List<Route> findByUserId(String userId){
		List<Route> routeList = new ArrayList<>();
		List<Route> routes = routeRepository.findByUserId(userId);
		for (Route route : routes){
			route.setThumbnail(makeThumbnailUrl(route.getRouteId()));
			route.setGeometry(readFile(route.getGeometry(), route.getUserId()));
			routeList.add(route);
		}
		return routeList;
	}
}
