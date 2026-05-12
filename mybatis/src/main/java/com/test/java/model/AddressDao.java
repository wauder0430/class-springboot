package com.test.java.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressDao {

	private final SqlSessionTemplate template;
	
	public int count() {
		
		return template.selectOne("address.count");
	}
	
}
