package com.test.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblUser")
public class User {

	@Id
	@Column(name = "seq")
	@SequenceGenerator(name = "seqUser", allocationSize = 1, sequenceName = "seqUser")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUser")
	private Long seq;
	
	// 교육중엔 @Column 생략했지만 프로젝트에선 할것
	private String username;
	private String name;
	private String email;
	private String role;
	private String provider;
	private String providerId;
	
}
