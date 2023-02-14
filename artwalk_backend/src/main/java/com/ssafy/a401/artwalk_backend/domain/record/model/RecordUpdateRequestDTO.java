package com.ssafy.a401.artwalk_backend.domain.record.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "기록 수정 요청 DTO")
public class RecordUpdateRequestDTO {
	@Schema(description = "기록 제목", example = "오운완~^^")
	private String title;
}
