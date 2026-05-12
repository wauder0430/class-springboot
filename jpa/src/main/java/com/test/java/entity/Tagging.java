package com.test.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblTagging")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tagging {
	
	@Id
	@Column(name = "seq")
	@SequenceGenerator(name = "seqTagging", allocationSize = 1, sequenceName = "seqTagging")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTagging")
	private Long seq;
	
	@ManyToOne
	@JoinColumn(name = "bseq")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name = "tseq")
	private Tag tag;
	

}
