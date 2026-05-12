package com.test.java.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.java.entity.User;

import static com.test.java.entity.QUser.user;
import static com.test.java.entity.QUserInfo.userInfo;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueryDSLRepository {

	private final JPAQueryFactory factory;

	public User m40() {
		
		/*
			조인
			- join()		: inner join
			- innerJoin()	: inner join
			- leftJoin()	: left outer join
			- rightJoin()	: right outer join
		
		*/
		
		return factory
				.selectFrom(user)
				.join(user.userInfo, userInfo)	// on 부모.멤버 = 자식
				.where(user.id.eq("hong"))
				.fetchOne();
	}

	public List<User> m41() {
		
		return factory
				.selectFrom(user)
				//.join(user.userInfo, userInfo)	// on 부모.멤버 = 자식
				//.innerJoin(user.userInfo, userInfo)
				.leftJoin(user.userInfo, userInfo)
				//.rightJoin(user.userInfo, userInfo)
				.fetch();
	}
	
}
