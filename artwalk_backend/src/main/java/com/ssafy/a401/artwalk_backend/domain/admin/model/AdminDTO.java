package com.ssafy.a401.artwalk_backend.domain.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.ssafy.a401.artwalk_backend.domain.user.model.UserAuthority;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import springfox.documentation.annotations.ApiIgnore;

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
