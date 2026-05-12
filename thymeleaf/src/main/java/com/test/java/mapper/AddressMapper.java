package com.test.java.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.java.model.AddressDto;

// 컨포넌트 스캔 > 스프링 빈
@Mapper
public interface AddressMapper {

	int count();
	
	AddressDto get(int seq); // template.selectOne()

	List<String> names();

	List<AddressDto> list();
	
}
