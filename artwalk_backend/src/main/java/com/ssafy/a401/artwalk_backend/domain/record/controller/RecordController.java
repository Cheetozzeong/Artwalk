package com.ssafy.a401.artwalk_backend.domain.record.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ssafy.a401.artwalk_backend.domain.record.model.Record;
import com.ssafy.a401.artwalk_backend.domain.record.service.RecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"기록 API"}, description = "기록 정보 API 입니다.")
@Controller
@RequestMapping("record")
@RequiredArgsConstructor
public class RecordController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";
	
	private final RecordService recordService;

	@Operation(summary = "공유이미지 조회", description = "공유이미지 조회 메서드입니다.")
	@ApiImplicitParam(name = "recordId", value = "조회할 기록 Id", dataType = "int")
	@GetMapping("/image/{recordId}")
	public String displayRecordImage(Model model, @PathVariable("recordId") int recordId) {
		try {
			Record record = recordService.findByRecordId(recordId);
			ResponseEntity<Resource> response = recordService.getShareImage(record);
			model.addAttribute("result", response);
			return "share/sharing";
		} catch (NullPointerException e) {
			return "error/4xx";
		}
	}

	@Operation(summary = "공유이미지 저장/갱신", description = "공유이미지 저장/갱신 메서드입니다.")
	@ApiImplicitParam(name = "recordId", value = "이미지를 생성/수정할 기록 Id", dataType = "int")
	@PostMapping("/image/{recordId}")
	public ResponseDTO displayRecordImage(@PathVariable("recordId") int recordId, @RequestBody Map<String, Object> request) {
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
