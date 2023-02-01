package com.ssafy.a401.artwalk_backend.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.a401.artwalk_backend.domain.admin.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
}
