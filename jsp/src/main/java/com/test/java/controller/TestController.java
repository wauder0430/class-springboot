package com.test.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {
	
	@GetMapping("/")
	public String index(Model model) {
		
		System.out.println("컨트롤러");
		
		return "index";
	}
	
}
