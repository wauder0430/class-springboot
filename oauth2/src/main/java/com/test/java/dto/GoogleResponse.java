package com.test.java.dto;

import java.util.Map;

public class GoogleResponse {

	private final Map<String, Object> attributes;
	
	public GoogleResponse(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	// 제공자
	public String getProvider() {
		return "google";
	}
	
	// 제공자에서 발급하는 아이디
	public String getProviderId() {
		return attributes.get("sub").toString();
	}

	// 이메일
	public String getEmail() {
		return attributes.get("email").toString();
	}
	
	// 이름
	public String getName() {
		return attributes.get("name").toString();
	}
	
	// 프로필
	public String getPicutre() {
		return attributes.get("picture").toString();
	}
	
}
