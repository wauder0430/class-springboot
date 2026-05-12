package com.test.java.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.test.java.dto.CustomOAuth2User;
import com.test.java.entity.User;
import com.test.java.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private final UserRepository repo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//System.out.println("SuccessHandler");
		
		// 지금 로그인 한 사람 누구?
		CustomOAuth2User customOAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		
		String username = customOAuth2User.getUsername();
		
		Optional<User> user = repo.findByUsername(username);
		
		if(user.isPresent()) {
			// 이미 등록된 사람
			response.sendRedirect("/");
		} else {
			// 처음 온 사람 > 회원 가입 페이지
			response.sendRedirect("/join");
		}
		
	}
	
}
