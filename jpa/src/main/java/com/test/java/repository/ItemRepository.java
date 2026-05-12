package com.test.java.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.java.entity.Item;
import com.test.java.model.ItemDto;

// 리포지토리
// - 역할: 엔티티 조작
// - 이름: 엔티티명 + Repository
// - extends: Jparespository<엔티티명, PK자료형>
public interface ItemRepository extends JpaRepository<Item, Long>{

	// select .. where name = ? 
	Item findByName(String name);

	Item findByNameIs(String name);

	Item findByNameEquals(String name);

	List<Item> findByColor(String color);

	List<Item> findByQty(int qty);

	Item findFirstByColor(String color);

	Item findFirstByQty(int qty);

	Item findTopByQty(int qty);

	List<Item> findTop3ByColor(String color);

	Item findByNameAndColor(String string, String string2);


	List<Item> findByColorOrQtyOrPrice(String string, int i, int j);

	List<Item> findByPriceGreaterThan(int i);

	List<Item> findByPriceGreaterThanEqual(int i);

	List<Item> findByPriceBetween(int i, int j);

	List<Item> findByPriceGreaterThanEqualAndColor(int i, String string);

	List<Item> findByQtyIsNull();

	List<Item> findByQtyIsNullAndDescriptionIsNull();

	List<Item> findByQtyIsNullOrDescriptionIsNull();

	List<Item> findByQtyIsNullOrDescriptionIsNullAndPriceGreaterThan(int i);

	List<Item> findByQtyIsNotNull();

	List<Item> findByColorIn(List<String> colors);

	List<Item> findByColorNotIn(List<String> colors);

	List<Item> findByNameStartsWith(String string);

	List<Item> findByNameEndsWith(String string);

	List<Item> findByNameContains(String string);

	List<Item> findByNameLike(String string);

	List<Item> findByDescriptionLike(String string);

	List<Item> findAllByOrderByNameAsc();

	List<Item> findByColorOrderByPriceAsc(String string);

	List<Item> findAllByOrderByColorAscPriceDesc();

	List<Item> findByPriceGreaterThanEqual(Sort by, Integer price);

	List<Item> findAllByOrderByPriceAsc();

	// JPQL
	// - select * from tblItem;
	@Query(value = "select m from Item m")
	List<Item> m25();

	// nativeQuery
	@Query(value = "select * from tblItem", nativeQuery = true)
	List<Item> m25_1();

	// JPQL
	@Query(value = "select m from Item m where m.color = :color")
	List<Item> m26(@Param(value="color") String color);

	@Query(value = "select m from Item m where m.color = :#{#dto.color} and m.price >= :#{#dto.price}")
	List<Item> m27(@Param(value="dto") ItemDto dto);

}
