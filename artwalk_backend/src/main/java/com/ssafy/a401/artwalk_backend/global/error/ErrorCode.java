package com.ssafy.a401.artwalk_backend.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// Token
	REISSUE_TOKEN(301, "T001", "New token is Issued"),
	ILLEGAL_TOKEN(401, "T002", "Token is Invalid"),
	EXPIRED_TOKEN(401, "T003", "Token is Expired"),
	EXPIRED_ACCESS_TOKEN(401, "T004", "Access Token is Expired"),
	UNSUPPORTED_TOKEN(401, "T005", "Token is not supported");

	private final String code;
	private final String message;
	private int status;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
}