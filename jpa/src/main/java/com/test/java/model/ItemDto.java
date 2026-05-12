package com.test.java.model;

import com.test.java.entity.Item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
	
	private Long seq;
	private String name;
	private Integer price;
	private String color;
	private Integer qty;
	private String description;
	
	public ItemDto(String name, String color, Integer qty) {
		this.name = name;
		this.color = color;
		this.qty = qty;
	}

	public Item toEntity() {
		return Item.builder()
				.seq(this.seq)
				.name(this.name)
				.price(this.price)
				.color(this.color)
				.qty(this.qty)
				.description(this.description)
				.build();
	}
	
}

