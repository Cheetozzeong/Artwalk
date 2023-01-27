package com.ssafy.a401.artwalk_backend.domain.record.service;

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
import java.util.Map;
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

import com.ssafy.a401.artwalk_backend.domain.record.model.Record;
import com.ssafy.a401.artwalk_backend.domain.record.repository.RecordRepository;

@Service
public class RecordService {
	@Autowired
	private RecordRepository recordRepository;

	private static String FILE_PATH;
	private static String MAPBOX_API_KEY;

	@Value("${file.path}")
	public void setFilePath(String filePath) {
		FILE_PATH = filePath + "record/";
	}

	@Value("${mapbox.api.key}")
	public void setMapboxApiKey(String mapboxApiKey) {
		MAPBOX_API_KEY = mapboxApiKey;
	}

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

	/** 파일 저장 후 기록을 반환합니다. */
	public static String saveFile(String value, String userId){
		String saveFolderPath = "";

		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		String saveFolder = today;

		File folder = new File(FILE_PATH + userId.replace('@', '_') + "/" + saveFolder);
		if (!folder.exists()){ // 기록 확인
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

	/** 인코딩된 기록(record)을 가지고 썸네일 이미지를 생성하고 저장한 후 저장된 경로를 반환합니다. */
	public static String makeThumbnail(String recordPath, String geometry, String userId) {
		int polyLineWidth = 5; // 기록 굵기
		String polyLineColor = "ff0000"; // 기록 색상
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
			StringTokenizer st = new StringTokenizer(recordPath, ".");
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
	public static String makeThumbnailUrl(int recordId) {
		String thumbUrl = "/record/thumb/" + recordId;
		return thumbUrl;
	}

	/** 인코딩된 기록(record)을 가지고 공유이미지를 생성하고 저장한 후 저장된 경로를 반환합니다. */
	public static String makeImage(String recordPath, String geometry, Map<String, Object> request, String userId) {
		int polyLineWidth = (int)request.get("polyLineWidth"); // 기록 굵기
		String polyLineColor = (String)request.get("polyLineColor"); // 기록 색상
		double minLon = (double)request.get("minLon"); // 최소 경도
		double minLat = (double)request.get("minLat"); // 최소 위도
		double maxLon = (double)request.get("maxLon"); // 최대 경도
		double maxLat = (double)request.get("maxLat"); // 최대 위도
		int imageWidth = 500; // 이미지 가로 크기
		int imageHeight = 500; // 이미지 세로 크기

		StringBuilder imageURL = new StringBuilder();
		imageURL.append("https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/path-")
			.append(polyLineWidth).append("+")
			.append(polyLineColor).append("(")
			.append(geometry).append(")/[")
			.append(minLon).append(",")
			.append(minLat).append(",")
			.append(maxLon).append(",")
			.append(maxLat).append("]/")
			.append(imageWidth).append("x")
			.append(imageHeight)
			.append("?access_token=")
			.append(MAPBOX_API_KEY);

		String filePathName = "";
		try {
			URL imgURL = new URL(imageURL.toString());
			String extension = "png";
			StringTokenizer st = new StringTokenizer(recordPath, ".");
			filePathName = "share_" + st.nextToken() + "." + extension;

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

	/** 공유이미지를 저장합니다. 만약 이미 저장되어 있는 이미지가 있는 경우 새로 만든 이미지로 교체합니다. */
	public Record saveRecordImage(Record record, Map<String, Object> request){
		Record result = null;

		String imagePath = makeImage(record.getThumbnail(), readFile(record.getGeometry(), record.getUserId()), request, record.getUserId());
		if(record.getRecentImage() != null && !("").equals(record.getRecentImage())){
			removeFile(record.getRecentImage(), record.getUserId());
		}
		record.setRecentImage(imagePath);
		result = recordRepository.save(record);

		return result;
	}

	/** 저장한 공유이미지를 반환합니다. */
	public static ResponseEntity<Resource> findImage(String imagePath) {
		ResponseEntity<Resource> response = null;

		Resource resource = new FileSystemResource(FILE_PATH + imagePath);
		if(!resource.exists()){
			response = new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try{
			filePath = Paths.get(FILE_PATH + imagePath);
			header.add("Content-type", Files.probeContentType(filePath));
			response = new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	/** 공유이미지 요청 경로를 반환합니다. */
	public static String makeImageUrl(int recordId) {
		String imageUrl = "/record/image/" + recordId;
		return imageUrl;
	}

	/** 기록을 저장합니다. */
	public Record addRecord(Record record){
		Record result = null;

		// TODO: 로그인 아이디 획득 및 저장하는 기능 추가
		record.setUserId("2007bae@naver.com"); // 임시로 계정 정보 등록

		String geometryPath = saveFile(record.getGeometry(), record.getUserId());
		record.setGeometry(geometryPath);

		String thumbPath = makeThumbnail(geometryPath, readFile(record.getGeometry(), record.getUserId()), record.getUserId());
		record.setThumbnail(thumbPath);

		result = recordRepository.save(record);

		return result;
	}

	/** 기록을 수정합니다. */
	public Record modifyRecord(Record originRecord, String detail){
		Record result = null;

		originRecord.setDetail(detail);
		result = recordRepository.save(originRecord);

		return result;
	}

	/** 기록을 삭제합니다. */
	public int removeRecord(Record record){
		recordRepository.delete(record);
		removeFile(record.getGeometry(), record.getUserId());
		removeFile(record.getThumbnail(), record.getUserId());
		removeFile(record.getRecentImage(), record.getUserId());
		int result = recordRepository.countByRecordId(record.getRecordId());
		return result;
	}

	/** 저장된 기록의 개수를 반환합니다. */
	public long findRecordCount(){
		long count = recordRepository.count();
		return count;
	}

	/** 저장된 모든 기록을 반환합니다. */
	public List<Record> findAllRecord(){
		List<Record> recordList = new ArrayList<>();
		List<Record> records = recordRepository.findAll();
		for (Record record : records){
			record.setThumbnail(makeThumbnailUrl(record.getRecordId()));
			record.setRecentImage(makeImageUrl(record.getRecordId()));
			record.setGeometry(readFile(record.getGeometry(), record.getUserId()));
			recordList.add(record);
		}
		return recordList;
	}

	/** 저장된 기록 중 record_id가 일치하는 기록를 반환합니다. */
	public Record findByRecordId(int recordId){
		Record record = recordRepository.findById(recordId).get();
		return record;
	}

	/** 저장된 기록 중 user_id가 일치하는 기록를 반환합니다. */
	public List<Record> findByUserId(String userId){
		List<Record> recordList = new ArrayList<>();
		List<Record> records = recordRepository.findByUserId(userId);
		for (Record record : records){
			record.setThumbnail(makeThumbnailUrl(record.getRecordId()));
			record.setRecentImage(makeImageUrl(record.getRecordId()));
			record.setGeometry(readFile(record.getGeometry(), record.getUserId()));
			recordList.add(record);
		}
		return recordList;
	}
}
