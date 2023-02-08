package com.ssafy.a401.artwalk_backend.domain.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(description = "관리자 DTO")
public class AdminDTO {
    @Schema(description = "관리자 아이디", required = false, example = "ssafy@ssafy.com")
    private String userId;

    @Schema(description = "관리자 비밀번호", required = true, example = "passwordpassword")
    private String password;
}
