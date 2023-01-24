package com.ssafy.a401.artwalk_backend.global.config;

import com.ssafy.a401.artwalk_backend.global.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ssafy.a401.artwalk_backend.domain.token.TokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// 	return new BCryptPasswordEncoder();
	// }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()

			// exception Handling
			.exceptionHandling()
			.accessDeniedHandler(jwtAccessDeniedHandler)
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)

			// JWT 사용해 세션 사용 안함
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			// 로그인, 회원가입 API는 토큰 없이도 허용
			.and()
			.authorizeRequests()
			.antMatchers("/**").permitAll()
//				.antMatchers("/login").permitAll()
//				.antMatchers("/members/").hasRole().
			.anyRequest().authenticated()


			.and()
				.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
//			.apply(new JwtSecurityConfig(tokenProvider));

		return http.build();
	}

	// @Bean
	// public WebSecurityCustomizer webSecurityCustomizer() {
	//
	// 	// // 관련 API 무시
	// 	// return (web) -> web.ignoring()
	// 	// 		.antMatchers("");
	//
	// }
}
