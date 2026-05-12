package com.test.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/template")
	public String template(Model model) {
		
		return "template";
	}
	
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
	
	
}
