package com.test.java.repository;

import static com.test.java.entity.QItem.item;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.java.entity.Item;
import com.test.java.entity.QItem;
import com.test.java.model.ItemDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemQueryDSLRepository {
	
	private final JPAQueryFactory factory; // statement, sqlsessiontemplate 역할

	public List<Item> m28() {
		
		//select m from Item m
		
		/*
			QClass 쿼리 작성
			- selectFrom(QClass): 해당 엔티티에서 모든 칼럼 가져오기
			
			결과셋 > 매핑 > 가져오기
			- fetch(): 리스트 조회(다중 행 가져오기). List<엔티티>. 결과가 없으면 빈 리스트 반환
			- fetchOne(): 단일행 조회(1개 행 가져오기). 엔티티. 결과가 2개 이상이면 에러 발생
			- fetchFirst():
			- fetchResults():
			- fetchCount():
		
		*/
		
		List<Item> list = factory
							.selectFrom(item)//SQL(JPQL) 생성 + ResultSet 생성
							.fetch();
		
		return list;
	}
	
	public Item m29(String name) {
		
		// Query DSL
		// - 테이블 > 엔티티(QClass)
		// - 컬럼 > 엔티티.필드(QClass.컬럼)
		
		return factory
					.selectFrom(item)
					.where(item.name.eq(name))//where name = ?
					.fetchOne();
	}

	public List<String> m30() {

		// 모든 컬럼: select * from > selectFrom()
		// 특정 컬럼: select name from > select() + from()
		
		return factory
					.select(item.name)	// select name
					.from(item)			// from tblItem
					.fetch();			// mapping
	}

	public List<Tuple> m31() {

		// - List > 자료형 동일
		// - Tuple > 자료형 상이
		
		return factory
					.select(item.name, item.color, item.qty)
					.from(item)
					.fetch();
	}

	public List<ItemDto> m32() {

		// select 결과 > 엔티티 > DTO
		// 준비물 > DTO 생성자(가져오고 싶은 컬럼만 매개변수 구성으로 된 생성자가 필요함)
		
		return factory.select(Projections.constructor(ItemDto.class, item.name, item.color, item.qty))
						.from(item)
						.fetch();
	}

	public List<Item> m33(ItemDto dto) {
		/*
		  	where
		 
		 	- 동등 비교
		 		-where(item.color.eq("white"))
		 	
		 	- 범위 비교 (숫자, 날짜)
		 		- where(item.price.gt(100000))
		 	
		 	- 열거형(in)
		 		- where(item.color.in("red", "yellow", "blue"))
		 	
		 	- 패턴 문자열
		 		- where(item.description.startsWith("최신"))
		 	
		 	- 논리 연산
		 		- and()
		 		- or()
		 		- not()
	 	
		 */
		
		
		return factory
				.selectFrom(item)
				//.where(item.color.eq("white"))
				//.where(item.color.ne("white"))
				//.where(item.description.isNull())
				//.where(item.description.isNotNull())
				//.where(item.price.gt(110000))
				//.where(item.price.goe(110000))
				//.where(item.price.lt(100000))
				//.where(item.price.loe(100000))
				//.where(item.price.between(50000, 100000))
				//.where(item.color.in("red", "yellow", "blue"))
				//.where(item.color.notIn("red", "yellow", "blue"))
				//.where(item.description.startsWith("최신"))
				//.where(item.description.endsWith("최신"))
				//.where(item.description.contains("최신"))
				//.where(item.description.like("%최신%"))
				.where(item.color.eq("white")
						.and(item.price.gt(100000))
						.and(item.qty.isNotNull()))
				.fetch();
	}

	public List<Item> m34() {
		
		/*
			정렬
			- orderBy(정렬기준)
			
			정렬기준
			- 엔티티.컬럼.기준()
				- asc()
				- desc()
				- nullsLast()
				- nullsFirst()
		
		*/
		
		return factory
				.selectFrom(item)
				//.orderBy(item.color.asc())
				//.orderBy(item.color.asc()
				//		, item.price.desc()
				//		, item.qty.asc())
				//.orderBy(item.qty.desc().nullsLast())
				.orderBy(item.qty.asc().nullsFirst())
				.fetch();
	}

	public List<Item> m35(int offset, int limit) {

		return factory
					.selectFrom(item)
					.offset(offset)
					.limit(limit)
					.fetch();
	}

	public Object m36() {
		
		// - count(), sum(), avg(), max(), min()
		
		return factory
				//.select(item.count())
				// .select(item.qty.sum())
				//select(item.qty.avg())
				//.select(item.qty.max())
				.select(item.qty.min())
				.from(item)
				.fetchOne();
	}

	public List<Tuple> m37() {
		
		// groupBy
		// having
		
		return factory
					.select(item.color, item.count(), item.price.avg())
					.from(item)
					.groupBy(item.color)
					.having(item.count().gt(5))
					.fetch();
	}

	public List<Item> m38() {
		
		// tblItem - 메인쿼리
		// tblItem - 서브쿼리
		// select * from tblItem a where price >= (select avg(price) from tblItem b);
		
		//- item  > tblItem 엔티티
		//- item2 > tblItem 엔티티
		//QItem item2 = QItem.item;
		QItem item2 = new QItem("item2");
		
		//where price>=(select avg(price) from tblItem)
		return factory
				.selectFrom(item)
				.where(item.price.goe(
					JPAExpressions.select(item2.price.avg()).from(item2)
				))
				.fetch();
	}

	public List<Tuple> m39() {
		
		// QItem item2 = QItem.item;
		QItem item2 = new QItem("item2");
		// select name, price, color, (select avg(price) from tblItem b where a.color = b.color) from tblItem a
		
		return factory
				.select(
						item.name, item.price, item.color,
						JPAExpressions
							.select(item2.price.avg())
							.from(item2)
							.where(item2.color.eq(item.color))
				)
				.from(item)
				.fetch();
		
	}
	
}