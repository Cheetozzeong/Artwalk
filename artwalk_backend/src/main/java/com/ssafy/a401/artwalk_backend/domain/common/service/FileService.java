package com.ssafy.a401.artwalk_backend.domain.common.service;

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
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	private static String FILE_PATH;
	private static String MAPBOX_API_URL;
	private static String MAPBOX_API_KEY;

	// TODO: 썸네일 세부 옵션 정하기 (임시 지정 상태)
	private static int polyLineWidth = 5;
	private static String polyLineColor = "ff0000";
	private static int imageWidth = 400;
	private static int imageHeight = 300;

	@Value("${file.path}")
	private void setFilePath(String filePath) {
		FILE_PATH = filePath;
	}
	@Value("${mapbox.api.url}")
	private void setMapboxApiUrl(String mapboxApiUrl) {
		MAPBOX_API_URL = mapboxApiUrl;
	}
	@Value("${mapbox.api.key}")
	private void setMapboxApiKey(String mapboxApiKey) {
		MAPBOX_API_KEY = mapboxApiKey;
	}

	public static String makePathName(String option, String filePath, String userId){
		return FILE_PATH + option + "/" + userId.replace('@', '_') + "/" + filePath;
	}

	public static String readFile(String option, String filePath, String userId){
		String result = "";
		File file = new File(makePathName(option, filePath, userId));
		if(file.exists()){
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				result = in.readLine();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String saveFile(String option, String value, String userId){
		String saveFolderPath = "";

		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		String saveFolder = today;

		File folder = new File(makePathName(option, saveFolder, userId));
		if (!folder.exists()){ // 경로 확인
			folder.mkdirs();
		}

		String time = Long.toString(System.currentTimeMillis());
		String rand = Integer.toString((int)(Math.random()*1000000));
		String saveFileName = time+rand+".txt";
		try {
			saveFolderPath = saveFolder + "/" + saveFileName;
			BufferedWriter fw = new BufferedWriter(new FileWriter(makePathName(option, saveFolderPath, userId), true));
			fw.write(value);
			fw.flush();
			fw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return saveFolderPath;
	}

	public static void removeFile(String option, String filePath, String userId){
		Path path = Paths.get(makePathName(option, filePath, userId));
		try {
			Files.delete(path);
		} catch (NoSuchFileException e) {
			System.out.println("삭제하려는 파일이 없습니다");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 프로필 주소를 가지고 이미지를 저장한 후 저장된 경로를 반환합니다. */
	public static String saveProfileImage(String imageURL, String userId) {
		String filePathName = "";
		try {
			URL imgURL = new URL(imageURL.toString());
			String extension = "png";
			filePathName = userId.replace('@', '_') + "_profile." + extension;

			BufferedImage image = ImageIO.read(imgURL);
			File file = new File(FILE_PATH + "profile/" + filePathName);
			if(!file.exists()) {
				file.mkdirs();
			}

			ImageIO.write(image, extension, file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePathName;
	}

	/** 저장한 프로필 이미지를 반환합니다. */
	public static ResponseEntity<Resource> findProfile(String userId) {
		ResponseEntity<Resource> response = null;

		String profilePath = userId.replace('@', '_') + "_profile.png";
		Resource resource = new FileSystemResource(FILE_PATH + "profile/" + profilePath);
		if(!resource.exists()){
			response = new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try{
			filePath = Paths.get(FILE_PATH + "profile/" + profilePath);
			header.add("Content-type", Files.probeContentType(filePath));
			response = new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	/** 인코딩된 경로(route)를 가지고 썸네일 이미지를 생성하고 저장한 후 저장된 경로를 반환합니다. */
	public static String saveThumbnail(String option, String geometryPath, String geometry, String userId) {
		StringBuilder imageURL = new StringBuilder();
		imageURL.append(MAPBOX_API_URL).append("path-")
			.append(polyLineWidth).append("+")
			.append(polyLineColor).append("(")
			.append(geometry).append(")/auto/")
			.append(imageWidth).append("x")
			.append(imageHeight).append("?access_token=")
			.append(MAPBOX_API_KEY);

		String filePathName = "";
		try {
			URL imgURL = new URL(imageURL.toString());
			String extension = "png";
			StringTokenizer st = new StringTokenizer(geometryPath, ".");
			filePathName = st.nextToken() + "." + extension;

			BufferedImage image = ImageIO.read(imgURL);
			File file = new File(makePathName(option, filePathName, userId));
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
	public static ResponseEntity<Resource> findThumbnail(String option, String thumbnailPath, String userId) {
		ResponseEntity<Resource> response = null;

		Resource resource = new FileSystemResource(makePathName(option, thumbnailPath, userId));
		if(!resource.exists()){
			response = new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try{
			filePath = Paths.get(makePathName(option, thumbnailPath, userId));
			header.add("Content-type", Files.probeContentType(filePath));
			response = new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	/** 인코딩된 기록(record)을 가지고 공유이미지를 생성하고 저장한 후 저장된 경로를 반환합니다. */
	public static String makeImage(String option, String recordPath, String geometry, Map<String, Object> request, String userId) {
		int polyLineWidth = (int)request.get("polyLineWidth"); // 기록 굵기
		String polyLineColor = (String)request.get("polyLineColor"); // 기록 색상
		double minLon = (double)request.get("minLon"); // 최소 경도
		double minLat = (double)request.get("minLat"); // 최소 위도
		double maxLon = (double)request.get("maxLon"); // 최대 경도
		double maxLat = (double)request.get("maxLat"); // 최대 위도

		StringBuilder imageURL = new StringBuilder();
		imageURL.append(MAPBOX_API_URL).append("path-")
			.append(polyLineWidth).append("+")
			.append(polyLineColor).append("(")
			.append(geometry).append(")/[")
			.append(minLon).append(",")
			.append(minLat).append(",")
			.append(maxLon).append(",")
			.append(maxLat).append("]/")
			.append(imageWidth).append("x")
			.append(imageHeight).append("?access_token=")
			.append(MAPBOX_API_KEY);

		String filePathName = "";
		try {
			URL imgURL = new URL(imageURL.toString());
			String extension = "png";
			StringTokenizer st = new StringTokenizer(recordPath, ".");
			filePathName = st.nextToken() + "_share." + extension;

			BufferedImage image = ImageIO.read(imgURL);
			File file = new File(makePathName(option, filePathName, userId));
			if(!file.exists()) {
				file.mkdirs();
			}

			ImageIO.write(image, extension, file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePathName;
	}

	/** 저장한 공유이미지를 반환합니다. */
	public static ResponseEntity<Resource> findImage(String option, String imagePath, String userId) {
		ResponseEntity<Resource> response = null;

		Resource resource = new FileSystemResource(makePathName(option, imagePath, userId));
		if(!resource.exists()){
			response = new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try{
			filePath = Paths.get(makePathName(option, imagePath, userId));
			header.add("Content-type", Files.probeContentType(filePath));
			response = new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
