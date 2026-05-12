package com.test.java.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblBoard")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	@Id
	@Column(name = "seq")
	@SequenceGenerator(name = "seqBoard", allocationSize = 1, sequenceName = "seqBoard")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqBoard")
	private Long seq;
	
	@Column(name = "subject", nullable = false, length = 1000)
	private String subject;
	
	@Column(name = "content", nullable = false, length = 4000)
	private String content;
	
	@Column(name = "regdate", nullable = false)
	private String regdate;
	
	// 게시물 1개와 관련된 유저는 몇명? > 1:1관계
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;
	
	@OneToMany(mappedBy = "board")
	private List<Tagging> tagging;

}
