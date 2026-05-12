package com.test.java.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.test.java.dto.CustomOAuth2User;
import com.test.java.dto.GoogleResponse;
import com.test.java.dto.UserDto;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	// 리소스 서버(구글)로부터 받아오는 개인 정보 > 메서드 호출
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
 		OAuth2User oAuth2User = super.loadUser(userRequest);
 		System.out.println("구글로부터 받아온 개인 정보 >>> " + oAuth2User);
		
 		
 		//구글 개인 정보 > 파싱 > 우리쪽 구조
 		GoogleResponse oauth2Response = new GoogleResponse(oAuth2User.getAttributes());
 		
 		
 		//자체 서비스 인증 처리
 		
 		//내부 아이디 생성
 		//- google hong
 		//- naver hong
 		
 		//username >>> google 103086981924944327104
 		String username = oauth2Response.getProvider() + " " + oauth2Response.getProviderId();
 		
 		System.out.println("username >>> " + username);
 		
 		
 		//인증 객체
 		UserDto dto = UserDto.builder()
 						.username(username)
 						.name(oauth2Response.getName())
 						.role("ROLE_MEMBER")
 						.email(oauth2Response.getEmail())
 						.provider(oauth2Response.getProvider())
 						.providerId(oauth2Response.getProviderId())
 						.build();
 		
		return new CustomOAuth2User(dto);
	}

}
