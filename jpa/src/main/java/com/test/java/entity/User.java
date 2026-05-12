package com.test.java.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblUser")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@Column(name = "id", length = 50)
	private String id;
	
	@Column(name = "pw", nullable = false, length = 50)
	private String pw;
	
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	
	@OneToOne(mappedBy = "user")
	//@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private UserInfo userInfo;
	
	@OneToMany(mappedBy = "user")
	//@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	//@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Board> board;

}
