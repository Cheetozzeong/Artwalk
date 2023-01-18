package com.ssafy.a401.artwalk_backend.domain.admin;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "admin")
public class Admin {

    @Id
    private String userId;

    @Column(nullable = false, length = 20)
    private String password;

}
