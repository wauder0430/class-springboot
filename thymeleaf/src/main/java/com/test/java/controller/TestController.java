package com.test.java.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.java.mapper.AddressMapper;
import com.test.java.model.AddressDto;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class TestController {

	// 스프링 부트 > AddressMapper 의존 주입 > 임시 자식 클래스 > 객체 주입
	private final AddressMapper mapper;
	
	@GetMapping("/m1")
	public String m1(Model model) {
		
		// 값을 전달 > 출력
		// 1. 단일값 
		int count = 200; 
		String name = "홍길동";
		
		model.addAttribute("count", count);
		model.addAttribute("name", name);
		
		
		
		// 2. 객체
		AddressDto dto = mapper.get(1);
		
		model.addAttribute("dto", dto);
		
		
		// 3. Map
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("red", "빨강");
		map.put("yellow", "노랑");
		map.put("blue", "파랑");
		
		model.addAttribute("map", map);
		
		return "m1";
	}
	
	
	@GetMapping(value =  "/m2")
	public String m2(Model model) {
	
		AddressDto dto = mapper.get(1);
		model.addAttribute("dto", dto);
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("red", "빨강");
		map.put("yellow", "노랑");
		map.put("blue", "파랑");
		
		model.addAttribute("map", map);
		
		return "m2";
	}
	
	@GetMapping(value = "/m3")
	public String m3(Model model) {
		
		// 타임리프 표현식 > 연산자
		int a = 10;
		int b = 3;
		
		//String name = "홍길동";//or null
		String name = null;
		
		model.addAttribute("a", a);
		model.addAttribute("b", b);
		
		model.addAttribute("name", name);
		
		return "m3";
	}
	
	
	@GetMapping(value = "/m4")
	public String m4(Model model) {
		
		model.addAttribute("count", 100);
		model.addAttribute("name", "강아지");
		model.addAttribute("color", "cornflowerblue");
		
		return "m4";
	}
	
	
	@GetMapping(value = "/m5")
	public String m5(Model model) {
		
		String txt1 = "홍길동입니다.";
		String txt2 = "<u>홍길동</u>입니다.";
		
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("red", "빨강");
		map.put("yellow", "노랑");
		map.put("blue", "파랑");
		
		List<String> names = mapper.names();
		List<AddressDto> list = mapper.list();
		
		model.addAttribute("map", map);
		model.addAttribute("txt1", txt1);
		model.addAttribute("txt2", txt2);
		model.addAttribute("num", 100);
		model.addAttribute("name", "홍길동");
		model.addAttribute("dto", mapper.get(1));
		model.addAttribute("names", names);
		model.addAttribute("list", list);
		
		return "m5";
	}
	
	
	@GetMapping(value = "/m6")
	public String m6(Model model) {
		
		int num1 = 1234567;
		double num2 = 1234.5678;
		Calendar now = Calendar.getInstance();
		
		model.addAttribute("num1", num1);
		model.addAttribute("num2", num2);
		model.addAttribute("now", now);
		
		
		return "m6";
	}
	
	@GetMapping(value = "/m7")
	public String m7(Model model) {
		
		return "m7";
	}
	
	@GetMapping(value = "/m8")
	public String m8(Model model) {
		
		int seq = 10;
		String mode = "add";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("search", "y");
		map.put("column", "subject");
		map.put("word", "java");
		
		model.addAttribute("seq", seq);
		model.addAttribute("mode", mode);
		model.addAttribute("map", map);
		
		return "m8";
	}
	
	
	@GetMapping(value = "/m9")
	public String m9(Model model) {
		
		int seq = 10;
		String mode = "add";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("search", "y");
		map.put("column", "subject");
		map.put("word", "java");
		
		List<String> names = mapper.names();
		List<AddressDto> list = mapper.list();
		
		model.addAttribute("seq", seq);
		model.addAttribute("mode", mode);
		model.addAttribute("map", map);
		model.addAttribute("names", names);
		model.addAttribute("list", list);
		
		return "m9";
	}
	
	@GetMapping(value = "/m10")
	public String m10(Model model) {
		
		return "m10";
	}
	
	/*
	@GetMapping(value = "/m")
	public String m(Model model) {
		
		return "m";
	}
	*/
}
