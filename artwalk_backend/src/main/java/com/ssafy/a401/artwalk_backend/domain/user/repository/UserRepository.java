package com.ssafy.a401.artwalk_backend.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.a401.artwalk_backend.domain.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	int countByRefreshToken(String refreshToken);
}
