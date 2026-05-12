package com.test.java.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

// 인증 객체
@Getter
public class CustomOAuth2User implements OAuth2User {

	private final UserDto dto;
	
	public CustomOAuth2User(UserDto dto) {
		this.dto = dto;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		// 사용 안함
		return null;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collection = new ArrayList<>();
		
		collection.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return dto.getRole();
			}
		});
		
		return collection;
	}
	
	@Override
	public String getName() {
		return dto.getName();
	}
	
	// 추가 정보
	public String getUsername() {
		return dto.getUsername();
	}
	
	public String getProvider() {
		return dto.getProvider();
	}
	
	public String getEmail() {
		return dto.getEmail();
	}
}
