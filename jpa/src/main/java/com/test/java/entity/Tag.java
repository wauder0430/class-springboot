package com.test.java.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblTag")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
	
	@Id
	@Column(name = "seq")
	@SequenceGenerator(name = "seqTag", allocationSize = 1, sequenceName = "seqTag")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTag")
	private Long seq;
	
	@Column(name = "tag", nullable = false, length = 100)
	private String tag;
	
	@OneToMany(mappedBy = "tag")
	private List<Tagging> tagging;

}
