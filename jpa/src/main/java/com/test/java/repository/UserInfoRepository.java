package com.test.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.java.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String>{

}
