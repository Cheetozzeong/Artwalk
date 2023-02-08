package com.ssafy.a401.artwalk_backend.domain.record.controller;

import java.io.IOException;
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
import com.ssafy.a401.artwalk_backend.domain.record.model.RecordResponseDTO;
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

@Controller
@RequestMapping("sharing")
@RequiredArgsConstructor
public class RecordController {
	private static final String OK = "Ok";
	private static final String FAIL = "Fail";
	
	private final RecordService recordService;

	@GetMapping("/{link}")
	public String sharingRecordPage(Model model, @PathVariable("link") String link) {
		try {
			recordService.findByLink(link);
			// ResponseEntity<Resource> response = recordService.getShareImage(record);
			model.addAttribute("result", link);
			return "share/sharing";
		} catch (NullPointerException e) {
			return "error/4xx";
		}
	}

	// @Operation(summary = "공유이미지 조회", description = "공유이미지 조회 메서드입니다. Path에 조회하려는 기록 ID를 포함하여 요청합니다.")
	// @ApiImplicitParam(name = "recordId", value = "조회할 기록 ID", dataType = "int")
	// @GetMapping("/image/{recordId}")
	// public String displayRecordImage(Model model, @PathVariable("recordId") int recordId) {
	// 	try {
	// 		Record record = recordService.findByRecordId(recordId);
	// 		ResponseEntity<Resource> response = recordService.getShareImage(record);
	// 		model.addAttribute("result", response.getBody());
	// 		return "share/sharing";
	// 	} catch (NullPointerException e) {
	// 		return "error/4xx";
	// 	}
	// }
}
