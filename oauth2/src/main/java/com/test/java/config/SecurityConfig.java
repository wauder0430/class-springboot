package com.test.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.test.java.service.CustomOAuth2LoginSuccessHandler;
import com.test.java.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2UserService service;
	private final CustomOAuth2LoginSuccessHandler handler;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		// CSRF 비활성화
		http.csrf(auth -> auth.disable());
		
		// Form Login > 사용 안함
		http.formLogin(auth -> auth.disable());
		http.httpBasic(auth -> auth.disable());

		// 허가 URL
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/", "/login/**", "/oauth2/**").permitAll()
			.anyRequest().authenticated()
		);
		
		// 로그인 시도 > 구글
		// OAuth2
		http.oauth2Login(auth -> auth
			.loginPage("/login")
			.successHandler(handler)
			.userInfoEndpoint(config -> config.userService(service))
		);
		
		// 로그아웃 추가
		http.logout(auth -> auth
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
		);
		
		
		return http.build();
	}
	
}
