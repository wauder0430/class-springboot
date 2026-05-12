package com.test.java.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.java.dto.CustomOAuth2User;
import com.test.java.entity.User;
import com.test.java.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final UserRepository repo;
	
	@GetMapping("/index")
	public String index(Model model) {
		
		return "index";
	}
	
	@GetMapping("/member")
	public String member(Model model) {
		
		return "member";
	}
	
	@GetMapping("/admin")
	public String admin(Model model) {
		
		return "admin";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		
		return "login";
	}
	
	@GetMapping("/join")
	public String join(Model model, @AuthenticationPrincipal CustomOAuth2User oauthUser) {
		
		//System.out.println(oauthUser.getDto());
		
		if(oauthUser != null) {
			model.addAttribute("name", oauthUser.getDto().getName());
			model.addAttribute("email", oauthUser.getDto().getEmail());
		}
		
		return "join";
	}
	
	@PostMapping("/joinok")
	public String joinok(
			@RequestParam("name") String name, 
			@RequestParam("email") String email, 
			@AuthenticationPrincipal CustomOAuth2User oauthUser) {
		
		User user = User.builder()
						.name(name)
						.email(email)
						.username(oauthUser.getUsername())
						.role(oauthUser.getDto().getRole())
						.provider(oauthUser.getProvider())
						.providerId(oauthUser.getDto().getProviderId())
						.build();
		
		repo.save(user);
		
		return "redirect:/";
	}
	
	
}
