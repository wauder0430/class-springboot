package com.test.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblUserInfo")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	
	@Id
	@Column(name = "id", length = 50)
	private String id;
	
	@Column(name = "age", nullable = false)
	private Integer age;
	
	@Column(name = "address", nullable = false, length = 500)
	private String address;
	
	@Column(name = "gender", length = 1)
	private String gender;
	
	//@어떤 관계(차수)
	@OneToOne
	//@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private User user; 

}
