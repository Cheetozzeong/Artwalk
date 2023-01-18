package com.ssafy.a401.artwalk_backend.global.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.a401.artwalk_backend.domain.token.TokenProvider;
import com.ssafy.a401.artwalk_backend.global.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private final TokenProvider tokenProvider;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtFilter customFilter = new JwtFilter(tokenProvider);
		// JwtFilter를 Security Filter 앞에 추가한다.
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
