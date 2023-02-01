package com.ssafy.a401.artwalk_backend.domain.record.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ssafy.a401.artwalk_backend.domain.common.model.ResponseDTO;
import com.ssafy.a401.artwalk_backend.domain.record.service.RecordService;
import com.ssafy.a401.artwalk_backend.domain.record.model.Record;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// @Tag(name = "기록", description = "Artwalk 기록 API 입니다.")
@RestController
@RequestMapping("record")
public class RecordRestController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";
	
	@Autowired
	private RecordService recordService;

	@Operation(summary = "기록 저장", description = "기록 저장 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 저장 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 저장 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PostMapping("")
	public ResponseDTO recordAdd(@RequestBody Record record, Authentication authentication) {
		ResponseDTO response = null;

		String userId = authentication.getName();
		Record result = recordService.addRecord(record, userId);
		if(result != null) {
			response = new ResponseDTO(OK, result);
		}else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "기록 목록 조회", description = "기록 목록 조회 메서드입니다. user가 true면 특정 사용자의 기록 목록만 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 목록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/list")
	public ResponseDTO recordList(@RequestParam(name="user") boolean userOption, Authentication authentication) {
		ResponseDTO response = null;
		List<Record> records = null;

		if(userOption) {
			String userId = authentication.getName();
			records = recordService.findByUserId(userId);
		} else {
			records = recordService.findAllRecord();
		}

		if(records != null) {
			response = new ResponseDTO(OK, records);
		} else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "기록 조회", description = "기록 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/{recordId}")
	public ResponseDTO recordDetails(@Parameter(name = "recordId", description = "기록 ID") @PathVariable("recordId") int recordId) {
		ResponseDTO response = null;

		Record record = recordService.findByRecordId(recordId);
		if(record != null) {
			record.setThumbnail(recordService.makeThumbnailUrl(record.getRecordId()));
			record.setRecentImage(recordService.makeImageUrl(record.getRecordId()));
			record.setGeometry(recordService.readGeometryFile(record));
			response = new ResponseDTO(OK, record);
		} else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "기록 수정", description = "기록 수정 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 수정 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 수정 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PutMapping("/{recordId}")
	public ResponseDTO recordModify(@Parameter(name = "recordId", description = "기록 ID") @PathVariable("recordId") int recordId, @RequestBody Record record) {
		ResponseDTO response = null;

		Record originRecord  = recordService.findByRecordId(recordId);
		Record result = recordService.modifyRecord(originRecord, record.getDetail());
		if(result != null) {
			response = new ResponseDTO(OK, result);
		} else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "기록 삭제", description = "기록 삭제 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 삭제 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@DeleteMapping("/{recordId}")
	public ResponseDTO recordRemove(@Parameter(name = "recordId", description = "기록 ID") @PathVariable("recordId") int recordId) {
		ResponseDTO response = null;

		Record record = recordService.findByRecordId(recordId);
		int result = recordService.removeRecord(record);
		if(result == 0) {
			response = new ResponseDTO(OK, result);
		} else {
			response = new ResponseDTO(FAIL, result);
		}

		return response;
	}

	@Operation(summary = "기록 개수 조회", description = "기록 개수 조회 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 개수 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/count")
	public ResponseDTO recordCount() {
		long count = recordService.getRecordCount();
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		ResponseDTO response = new ResponseDTO(OK, map);
		return response;
	}

	@Operation(summary = "기록 썸네일 조회", description = "기록 썸네일 조회 메서드입니다.")
	@GetMapping("/thumb/{recordId}")
	public ResponseEntity<Resource> displayRecordThumbnail(@Parameter(name = "recordId", description = "기록 ID") @PathVariable("recordId") int recordId) {
		Record record = recordService.findByRecordId(recordId);
		ResponseEntity<Resource> response = recordService.getThumbnailImage(record);
		return response;
	}

	@Operation(summary = "공유이미지 조회", description = "공유이미지 조회 메서드입니다.")
	@GetMapping("/image/{recordId}")
	public ResponseEntity<Resource> displayRecordImage(@Parameter(name = "recordId", description = "기록 ID") @PathVariable("recordId") int recordId) {
		Record record = recordService.findByRecordId(recordId);
		ResponseEntity<Resource> response = recordService.getShareImage(record);
		return response;
	}

	@Operation(summary = "공유이미지 저장/갱신", description = "공유이미지 저장/갱신 메서드입니다.")
	@PostMapping("/image/{recordId}")
	public ResponseDTO displayRecordImage(@Parameter(name = "recordId", description = "기록 ID") @PathVariable("recordId") int recordId, @RequestBody Map<String, Object> request) {
		ResponseDTO response = null;

		Record record = recordService.findByRecordId(recordId);
		Record result = recordService.saveRecordImage(record, request);

		if(result != null) {
			response = new ResponseDTO(OK, result);
		} else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}
}
