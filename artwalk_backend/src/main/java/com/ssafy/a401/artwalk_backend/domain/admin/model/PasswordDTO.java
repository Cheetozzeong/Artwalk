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
@Schema(description = "비밀번호 DTO")
public class PasswordDTO {
    @Schema(description = "비밀번호", required = true, example = "password")
    private String password;
}
