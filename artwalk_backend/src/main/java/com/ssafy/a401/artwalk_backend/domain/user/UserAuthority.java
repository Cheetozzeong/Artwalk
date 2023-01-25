package com.ssafy.a401.artwalk_backend.domain.user;

public enum UserAuthority {
	ROLE_USER {
		@Override
		public String toString() {
			return "ROLE_USER";
		}
	},
	ROLE_ADMIN {
		@Override
		public String toString() {
			return "ROLE_ADMIN";
		}
	}
}
