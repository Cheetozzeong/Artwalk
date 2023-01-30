package com.ssafy.a401.artwalk_backend.domain.record.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.a401.artwalk_backend.domain.common.service.FileService;
import com.ssafy.a401.artwalk_backend.domain.record.model.Record;
import com.ssafy.a401.artwalk_backend.domain.record.repository.RecordRepository;

@Service
public class RecordService {
	@Autowired
	private RecordRepository recordRepository;
	private FileService fileService = new FileService("record");

	public Record addRecord(Record record, String userId) {
		Record result = null;

		record.setUserId(userId);

		String geometryPath = fileService.saveFile(record.getGeometry(), record.getUserId());
		record.setGeometry(geometryPath);

		String thumbPath = fileService.saveThumbnail(geometryPath, fileService.readFile(record.getGeometry(), record.getUserId()), record.getUserId());
		record.setThumbnail(thumbPath);

		result = recordRepository.save(record);

		return result;
	}

	public Record modifyRecord(Record originRecord, String detail) {
		Record result = null;

		originRecord.setDetail(detail);
		result = recordRepository.save(originRecord);

		return result;
	}

	public int removeRecord(Record record) {
		recordRepository.delete(record);
		fileService.removeFile(record.getGeometry(), record.getUserId());
		fileService.removeFile(record.getThumbnail(), record.getUserId());
		fileService.removeFile(record.getRecentImage(), record.getUserId());
		int result = recordRepository.countByRecordId(record.getRecordId());
		return result;
	}

	/** 저장된 기록의 개수를 반환합니다. */
	public long getRecordCount() {
		long count = recordRepository.count();
		return count;
	}

	/** 저장된 모든 기록을 반환합니다. */
	public List<Record> findAllRecord() {
		List<Record> recordList = new ArrayList<>();
		List<Record> records = recordRepository.findAll();
		for (Record record : records) {
			record.setThumbnail(makeThumbnailUrl(record.getRecordId()));
			record.setRecentImage(makeImageUrl(record.getRecordId()));
			record.setGeometry(fileService.readFile(record.getGeometry(), record.getUserId()));
			recordList.add(record);
		}
		return recordList;
	}

	/** 저장된 기록 중 record_id가 일치하는 기록를 반환합니다. */
	public Record findByRecordId(int recordId) {
		Record record = recordRepository.findById(recordId).get();
		return record;
	}

	/** 저장된 기록 중 user_id가 일치하는 기록를 반환합니다. */
	public List<Record> findByUserId(String userId) {
		List<Record> recordList = new ArrayList<>();
		List<Record> records = recordRepository.findByUserId(userId);
		for (Record record : records) {
			record.setThumbnail(makeThumbnailUrl(record.getRecordId()));
			record.setRecentImage(makeImageUrl(record.getRecordId()));
			record.setGeometry(fileService.readFile(record.getGeometry(), record.getUserId()));
			recordList.add(record);
		}
		return recordList;
	}

	public String readGeometryFile(Record record) {
		return fileService.readFile(record.getGeometry(), record.getUserId());
	}

	public ResponseEntity<Resource> getThumbnailImage(Record record) {
		return fileService.findThumbnail(record.getThumbnail(), record.getUserId());
	}

	public ResponseEntity<Resource> getShareImage(Record record) {
		return fileService.findImage(record.getRecentImage());
	}

	/** 썸네일 요청 경로를 반환합니다. */
	public static String makeThumbnailUrl(int recordId) {
		String thumbUrl = "/record/thumb/" + recordId;
		return thumbUrl;
	}

	/** 공유이미지 요청 경로를 반환합니다. */
	public static String makeImageUrl(int recordId) {
		String imageUrl = "/record/image/" + recordId;
		return imageUrl;
	}

	/** 공유이미지를 저장합니다. 만약 이미 저장되어 있는 이미지가 있는 경우 새로 만든 이미지로 교체합니다. */
	public Record saveRecordImage(Record record, Map<String, Object> request) {
		Record result = null;

		String imagePath = fileService.makeImage(record.getThumbnail(), fileService.readFile(record.getGeometry(), record.getUserId()), request, record.getUserId());
		if(record.getRecentImage() != null && !("").equals(record.getRecentImage())) {
			fileService.removeFile(record.getRecentImage(), record.getUserId());
		}
		record.setRecentImage(imagePath);
		result = recordRepository.save(record);

		return result;
	}
}
