package com.ssafy.a401.artwalk_backend.domain.record.controller;

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

import com.ssafy.a401.artwalk_backend.domain.admin.service.AdminService;
import com.ssafy.a401.artwalk_backend.domain.common.model.ResponseDTO;
import com.ssafy.a401.artwalk_backend.domain.record.service.RecordService;
import com.ssafy.a401.artwalk_backend.domain.record.model.Record;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"기록 API"}, description = "기록 정보 API 입니다.")
@RestController
@RequestMapping("record")
@RequiredArgsConstructor
public class RecordRestController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";
	
	private final RecordService recordService;
	private final AdminService adminService;

	@Operation(summary = "기록 저장", description = "기록 저장 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 저장 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 저장 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PostMapping("")
	public ResponseDTO recordAdd(@RequestBody Record record, @ApiIgnore Authentication authentication) {
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

	@Operation(summary = "기록 목록 조회", description = "기록 목록 조회 메서드입니다.")
	@ApiImplicitParam(name = "user", value = "true: accessToken과 일치하는 사용자의 기록 목록을 반환합니다. ||  false: 모든 사용자의 기록 목록을 반환합니다.", dataType = "boolean")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 목록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/list")
	public ResponseDTO recordList(@RequestParam(name="user") boolean userOption, @ApiIgnore Authentication authentication) {
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

	@Operation(summary = "기록 검색", description = "기록 검색 메서드입니다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "경로 검색 옵션", dataType = "String"),
			@ApiImplicitParam(name = "keyword", value = "경로 검색 키워드", dataType = "String")
	})
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "기록 목록 조회 성공"),
			@ApiResponse(responseCode = FAIL, description = "기록 목록 조회 실패")
	})
	@GetMapping("/search")
	public ResponseDTO recordListSearch(@RequestParam(name = "type") String type, @RequestParam(name = "keyword") String keyword) {
		ResponseDTO response = null;
		List<Record> records = null;

		if(type.equals("userId")) {
			String userId = keyword;
			records = recordService.findByUserIdContaining(userId);
		} else if (type.equals("detail")) {
			String detail = keyword;
			records = recordService.findByDetailContaining(detail);
		}

		if(records != null) {
			response = new ResponseDTO(OK, records);
		}else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "관리자용 사용자 기록 목록 조회", description = "관리자용 특정 사용자 기록 목록 조회 메서드입니다.")
	@ApiImplicitParam(name = "userId", value = "기록 목록을 조회할 사용자 ID (예시. ssafy@ssafy.com)", dataType = "String")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OK, description = "기록 목록 조회 성공"),
			@ApiResponse(responseCode = FAIL, description = "기록 목록 조회 실패")
	})
	@GetMapping("/list/{userId}")
	public ResponseDTO recordListByUserId(@PathVariable("userId") String userId){
		ResponseDTO response = null;
		List<Record> records = null;

		records = recordService.findByUserId(userId);

		if(records != null) {
			response = new ResponseDTO(OK, records);
		}else {
			response = new ResponseDTO(FAIL, null);
		}

		return response;
	}

	@Operation(summary = "관리자용 사용자 기록 삭제", description = "관리자용 사용자 기록 삭제 메서드입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "관리자용 사용자 기록 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "관리자용 사용자 기록 삭제 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@DeleteMapping("/admin/{recordId}")
	public ResponseDTO recordRemoveAdmin(@RequestBody Map<String, String> passwordMap, @PathVariable("recordId") int recordId, Authentication authentication) {
		ResponseDTO response = null;

		Record record = recordService.findByRecordId(recordId);
		String userId = authentication.getName();
		int result = adminService.checkPw(userId, passwordMap.get("password"));

		if (result == 0) {
			int res = recordService.removeRecord(record);
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

	@Operation(summary = "기록 조회", description = "기록 조회 메서드입니다.")
	@ApiImplicitParam(name = "recordId", value = "조회할 기록 Id", dataType = "int")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@GetMapping("/{recordId}")
	public ResponseDTO recordDetails(@PathVariable("recordId") int recordId) {
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
	@ApiImplicitParam(name = "recordId", value = "수정할 기록 Id", dataType = "int")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 수정 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 수정 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@PutMapping("/{recordId}")
	public ResponseDTO recordModify(@PathVariable("recordId") int recordId, @RequestBody Record record) {
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
	@ApiImplicitParam(name = "recordId", value = "삭제할 기록 Id", dataType = "int")
	@ApiResponses(value = {
		@ApiResponse(responseCode = OK, description = "기록 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
		@ApiResponse(responseCode = FAIL, description = "기록 삭제 실패", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	})
	@DeleteMapping("/{recordId}")
	public ResponseDTO recordRemove(@PathVariable("recordId") int recordId) {
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
	@ApiImplicitParam(name = "recordId", value = "조회할 기록 Id", dataType = "int")
	@GetMapping("/thumb/{recordId}")
	public ResponseEntity<Resource> displayRecordThumbnail(@PathVariable("recordId") int recordId) {
		Record record = recordService.findByRecordId(recordId);
		ResponseEntity<Resource> response = recordService.getThumbnailImage(record);
		return response;
	}

	// @Operation(summary = "공유이미지 조회", description = "공유이미지 조회 메서드입니다.")
	// @ApiImplicitParam(name = "recordId", value = "조회할 기록 Id", dataType = "int")
	// @GetMapping("/image/{recordId}")
	// public ResponseEntity<Resource> displayRecordImage(@PathVariable("recordId") int recordId) {
	// 	Record record = recordService.findByRecordId(recordId);
	// 	ResponseEntity<Resource> response = recordService.getShareImage(record);
	// 	return response;
	// }
	//
	// @Operation(summary = "공유이미지 저장/갱신", description = "공유이미지 저장/갱신 메서드입니다.")
	// @ApiImplicitParam(name = "recordId", value = "이미지를 생성/수정할 기록 Id", dataType = "int")
	// @PostMapping("/image/{recordId}")
	// public ResponseDTO displayRecordImage(@PathVariable("recordId") int recordId, @RequestBody Map<String, Object> request) {
	// 	ResponseDTO response = null;
	//
	// 	Record record = recordService.findByRecordId(recordId);
	// 	Record result = recordService.saveRecordImage(record, request);
	//
	// 	if(result != null) {
	// 		response = new ResponseDTO(OK, result);
	// 	} else {
	// 		response = new ResponseDTO(FAIL, null);
	// 	}
	//
	// 	return response;
	// }
}
