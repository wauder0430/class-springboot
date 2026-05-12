package com.test.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QueryDSLConfig {

	private final EntityManager em; // JPA의 쿼리 실행

	/*<bean class="com.querydsl.jpa.impl.JPAQueryFactory">
		<constructor-arg ref="em">
	</bean>*/
	
	@Bean
	public JPAQueryFactory jPAQueryFactory() {
		
		return new JPAQueryFactory(em);
	}
	
}
