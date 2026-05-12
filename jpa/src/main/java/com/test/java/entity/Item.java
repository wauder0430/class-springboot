package com.test.java.entity;

import com.test.java.model.ItemDto;

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

/*
	
	ERD > 테이블 > Entity
	
	엔티티(Entity) 클래스
	- 역할: tblItem 테이블을 자바와 중계해주는 역할
	- 자바에서 Item 클래스 조작 > (개발자X) > tblItem 테이블 반영
	
	
	DTO > 아무 기능이 없는 데이터를 담는 상자
	Entity > 기능이 있는 상자
	
*/

@Entity
@Getter
//@Setter
@ToString
@Builder
@NoArgsConstructor	// 엔티티는 기본 생성자 필수 
@AllArgsConstructor
@Table(name = "tblItem")	// 테이블 매핑
public class Item {
	
	@Id //PK
	@Column(name = "seq") //실제 테이블의 컬럼명. 동일하면 생략 가능
	@SequenceGenerator(name = "seqItem", allocationSize = 1, sequenceName = "seqItem")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqItem")
	private Long seq;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "price", nullable = false)
	private Integer price;
	
	@Column(name = "color", nullable = false)
	private String color;
	
	@Column(name = "qty", nullable = true)
	private Integer qty;
	
	@Column(name = "description", nullable = false, length = 1000)
	private String description;
	
	// 매핑 메서드
	public ItemDto toDto() {
		return ItemDto.builder()
				.seq(this.seq)
				.name(this.name)
				.price(this.price)
				.color(this.color)
				.qty(this.qty)
				.description(this.description)
				.build();
	}
	
	// Setter 역할
	public void update(String name, Integer price, String color, Integer qty, String description) {
		this.name = name;
		this.price = price;
		this.color = color;
		this.qty = qty;
		this.description = description;
	}
	
}
