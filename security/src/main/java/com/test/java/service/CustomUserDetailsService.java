package com.test.java.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.java.dto.CustomUserDetails;
import com.test.java.entity.Member;
import com.test.java.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Member> member = repo.findById(username);
		
		if(member.isPresent()) {
			
			return new CustomUserDetails(member.get());  	// 인증 객체
		} else {
			
			return null; 	// 로그인 실패
		}
		
	}

}
