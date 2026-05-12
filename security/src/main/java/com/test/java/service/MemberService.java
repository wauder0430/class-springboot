package com.test.java.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.java.dto.MemberDto;
import com.test.java.entity.Member;
import com.test.java.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository repo;
	private final BCryptPasswordEncoder encoder;

	public void join(MemberDto dto) {
		
		Member member = Member.builder()
							.username(dto.getUsername())
							.password(encoder.encode(dto.getPassword()))
							.role(dto.getRole())
							.age(dto.getAge())
							.email(dto.getEmail())
							.build();
		
		repo.save(member);
		
	}
	
	
	
}
