package com.test.java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	
	private String username;
	private String password;
	private Integer age;
	private String email;
	private String role;
	
}
