package com.test.java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private Long seq;
	private String username;
	private String name;
	private String email;
	private String role;
	private String provider;
	private String providerId;
	
}
