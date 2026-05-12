package com.test.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//<security:http> 설정
		
		// 7. URI 허가하기
		// - requestMatchers() 주의점. 같은 URL을 2번 이상 정의하면 첫 정의만 적용
		http.authorizeHttpRequests(auth -> auth
//			.requestMatchers("/").permitAll()
//			.requestMatchers("/login").permitAll()
//			.requestMatchers("/join").permitAll()
//			.requestMatchers("/joinok").permitAll()
			.requestMatchers("/", "/login", "/join", "/joinok").permitAll()
			.requestMatchers("/member").hasAnyRole("MEMBER","ADMIN") // ROLE_MEMBER
			.requestMatchers("/admin").hasRole("ADMIN") // ROLE_ADMIN
			.anyRequest().authenticated()
		);
		
		// 개발 > CSRF 비활성화
		//http.csrf(auth -> auth.disable());
		
		// 커스텀 로그인 페이지 설정
		http.formLogin(auth -> auth
			.loginPage("/login")				// 내가 만든 로그인 페이지
			.loginProcessingUrl("/loginok")		// 로그인 처리 URL
		);
		
		// 예외 처리 핸들러
		http.exceptionHandling(auth -> auth
			.authenticationEntryPoint((request, response, exception) -> {
				// 401
				// - 익명 사용자 접근 > 로그인 페이지로 강제 이동
				response.sendRedirect("/login");
			})
			.accessDeniedHandler((request, response, exception) -> {
				// 403
				// - 인증 유저 + 권한 없음
				response.sendRedirect("/denied");
			})
		);
		
		// 로그아웃 
		// - GET
		// - /logout
		http.logout(auth -> auth
				.logoutRequestMatcher(
					PathPatternRequestMatcher.withDefaults().matcher("/logout")
				)
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
		);
		
		return http.build();
	}
	
	// 
	@Bean
	BCryptPasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	
}
