package com.ssafy.a401.artwalk_backend.global.util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {
	private SecurityUtil() {}

	public static Long getCurrentMemberId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
		}

		return Long.parseLong(authentication.getName());
	}
}
