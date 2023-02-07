package com.ssafy.a401.artwalk_backend.domain.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.ssafy.a401.artwalk_backend.domain.user.model.UserAuthority;

@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@ToString
@Entity(name = "admin")
@Schema(description = "관리자 Entity")
public class Admin {

    @Id
    private String userId;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = true, length = 255)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("ROLE_ADMIN")
    private UserAuthority userAuthority;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
