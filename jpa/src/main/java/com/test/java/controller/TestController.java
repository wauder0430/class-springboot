package com.test.java.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.Tuple;
import com.test.java.entity.Board;
import com.test.java.entity.Item;
import com.test.java.entity.User;
import com.test.java.entity.UserInfo;
import com.test.java.model.ItemDto;
import com.test.java.repository.BoardRepository;
import com.test.java.repository.ItemQueryDSLRepository;
import com.test.java.repository.ItemRepository;
import com.test.java.repository.TagRepository;
import com.test.java.repository.UserInfoRepository;
import com.test.java.repository.UserQueryDSLRepository;
import com.test.java.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
	
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;
	private final UserInfoRepository userInfoRepository;
	private final BoardRepository boardRepository;
	private final TagRepository tagRepository;
	
	private final ItemQueryDSLRepository itemQueryDSLRepository;
	private final UserQueryDSLRepository userQueryDSLRepository;
	
	/*
		DB 조작
		1. JDBC
		2. MyBatis
		3. JPA
		
		JPA 쿼리 실행 방식
		
		1. Query Method
			- 30~40%
			- 단순 업무
		
		2. JPQL, Java Persistence Query Language 
			- 30~40%
			- 복잡한 업무
		
		3. Query DSL
			- 10~20%
			- 아주 복잡한 업무

	*/

	@GetMapping("/m1")
	public String m1(Model model) {
		
		return "add";
	}
	
	@PostMapping("/m1ok")
	public String m1ok(Model model, ItemDto dto) {
	
		//System.out.println(dto);
		
		//[C]RUD
		// - 레코드 추가하기(insert)
		// - 새로 추가할 레코드 > 엔티티 생성(***)
		
		// ORM
		// 개발자 > (자바)객체 조작 > (결과) > DB적용
		
		// DTO > (변환, 매핑) > Entity
		// 생성자 > 불편
		// Item item = new Item(dto.getSeq(), dto.getName(), dto.getPrice(), dto.getColor(), dto.getQty(), dto.getDescription());
		
		// 빌더패턴 > 편함 + 자유로움
		/* Item item = Item.builder()
						.seq(dto.getSeq())
						.name(dto.getName())
						.price(dto.getPrice())
						.color(dto.getColor())
						.qty(dto.getQty())
						.description(dto.getDescription())
						.build();
		*/
		
		Item item = dto.toEntity();
		
		itemRepository.save(item);
		
		return "result";
	}
	
	@GetMapping("/m2")
	public String m2(Model model) {
		
		// C[R]UD
		// - 1개의 레코드 가져오기
		
		// - select * from tblItem where seq = 1;
		
		// Hibernate: select i1_0.seq,i1_0.color,i1_0.description,i1_0.name,i1_0.price,i1_0.qty from tblItem i1_0 where i1_0.seq=?
		Optional<Item> item = itemRepository.findById(1L);
		
		// System.out.println(item);
		
		if(item.isPresent()) {
			// Entity > (매핑) > Dto
			model.addAttribute("dto", item.get().toDto());
		}
		
		return "result";
	}
	
	@GetMapping("/m3")
	public String m3(Model model, @RequestParam("seq") Long seq) {
		
		// m3?seq = 10
		Optional<Item> item = itemRepository.findById(seq);
		
		item.ifPresent(value -> model.addAttribute("dto", value.toDto()));
		
		return "edit";
	}
	
	/*
	 * @PostMapping("/m3ok") public String m3ok(Model model, ItemDto dto) {
	 * 
	 * // CR[U]D // - 레코드 수정하기 // 1. 엔티티를 직접 생성 > 값을 수정 > 수정하기 // 2. 검색 > 엔티티 반환 >
	 * 값을 수정 > 수정하기
	 * 
	 * // 1. 엔티티를 직접 생성 > 값을 수정 > 수정하기 Item item = Item.builder() .seq(dto.getSeq())
	 * .name(dto.getName()) .price(dto.getPrice()) .color(dto.getColor())
	 * .qty(dto.getQty()) .description(dto.getDescription()) .build();
	 * 
	 * // save() 호출 // 1. select .. where seq = ? 있으면 // 2. update
	 * 
	 * // 1. select .. where seq = ? 없으면 // 2. insert itemRepository.save(item);
	 * 
	 * 
	 * 
	 * 
	 * // 2. 검색 > 엔티티 반환 > 값을 수정 > 수정하기
	 * 
	 * 
	 * return "result"; }
	 */
	
	@PostMapping("/m3ok")
	public String m3ok(Model model, ItemDto dto) {
		
		// CR[U]D
		// - 레코드 수정하기
		// 1. 엔티티를 직접 생성 > 값을 수정 > 수정하기
		// 2. 검색 > 엔티티 반환 > 값을 수정 > 수정하기
		
		// 2. 검색 > 엔티티 반환 > 값을 수정 > 수정하기
		
		Optional<Item> item = itemRepository.findById(dto.getSeq());
		
		Item result = item.get();
		
		// *** JPA를 대하는 개발자 태도
		// - dto.setName("이름") > 이후 발생하는 일들 > 다양
		// - entity.setName("이름") > 반드시 DB에 반영 (인지하고 써라)
		result.update(dto.getName(), dto.getPrice(), dto.getColor(), dto.getQty(), dto.getDescription());
		// 엔티티 > 수정
		
		itemRepository.save(result);
		
		return "result";
	}
	
	
	@GetMapping("/m4")
	public String m4(Model model, @RequestParam("seq") Long seq) {
		
		// m4?seq=1
		
		// CRU[D]
		// - 레코드 삭제하기
		
		// 1. 엔티티 직접 생성 후 > 삭제
		// 2. 엔티티 검색 후 > 삭제
		/*
		 	Item item = Item.builder()
						.seq(seq)
						.build();
						
			itemRepository.delete(item);
		*/
		
		Optional<Item> item = itemRepository.findById(seq);
		
		if(item.isPresent()) {
			itemRepository.delete(item.get());
		}
		
		return "result";
		
	}
	
	
	@GetMapping("/m5")
	public String m5(Model model) {
		
		/*
			1. Query Method
			- 정해진 키워드 > 사용 > 메서드명 생성 > 호출 > 메서드명에 따라 정해진 SQL이 실행
			- 패턴: 정해진 행동 키워드 + 컬럼명
			
		*/
		
		// Optional<Item> item = itemRepository.findById(3L);
		
		// where name = ? 
		// Optional<Item> item = itemRepository.findByName("스마트워치");
		
		Item item = itemRepository.findByName("스마트워치"); // 정해진 키워드: findBy
		
		//model.addAttribute("dto", item.get().toDto());
		model.addAttribute("dto", item.toDto());
		
		return "result";
	}
	
	@GetMapping("/m6")
	public String m6(Model model) {
		
		// select count(*)
		Long count = itemRepository.count();
		
		// 레코드 존재 유무
		Boolean result = itemRepository.existsById(1L);
		
		model.addAttribute("count", count);
		model.addAttribute("result", result);
		
		return "result";
	}
	
	@GetMapping("/m7")
	public String m7(Model model) {
		
		// 전체 레코드 가져오기
		// - select * from tblItem
		List<Item> list = itemRepository.findAll();
		
		// List<엔티티> >> List<DTO>
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m8")
	public String m8(Model model) {
		
		// m5
		
		// Is, Equals
		// - 동등 비교
		
		// Item item = itemRepository.findByName("전기 스쿠터");
		// Item item = itemRepository.findByNameIs("전기 스쿠터");
		// Item item = itemRepository.findByNameEquals("전기 스쿠터");
		
		// model.addAttribute("dto", item.toDto());
		
		// where color = ?
		// Item item =itemRepository.findByColor("yellow");
		// 복수개의 결과를 받을걸 예상하고 list로 받기
		// List<Item> list =itemRepository.findByColor("yellow");
		List<Item> list =itemRepository.findByQty(20);
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		// model.addAttribute("dto", item.toDto());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m9")
	public String m9(Model model) {
		
		// First, Top
		// - 가져올 레코드의 개수를 지정한다.
		// - 결과셋에서 위에서부터 몇개를 가져올지?
		
		// - select * from tblItem where rownum <= 3;	// Oracle
		// - select * from tblItem limit 0,3;			// My-sql
		// - select top 3 * from tblItem;				// MS-sql
		
		// Item item = itemRepository.findFirstByColor("yellow");
		// Item item = itemRepository.findFirstByQty(20);
		// Item item = itemRepository.findTopByQty(20);
		
		// model.addAttribute("dto", item.toDto());
		
		// where color=? and rownum <=3
		// where color=? fetch first 3 rows only
		List<Item> list = itemRepository.findTop3ByColor("white");
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	
	@GetMapping("/m10")
	public String m10(Model model) {
		
		// And, Or
		// Item item = itemRepository.findByNameAndColor("키보드", "blue");
		
		// model.addAttribute("dto", item.toDto());
		
		List<Item> list = itemRepository.findByColorOrQtyOrPrice("yellow", 20, 85000);
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m11")
	public String m11(Model model) {
		
		// After, Before: 날짜시간 비교
		// GreaterThan(GreaterThanEqual), LessThan(LessThanEqual): 숫자 비교
		// Between: 날짜시간, 숫자 비교
		
		// After, GreaterThen(GreaterThanEqual): 크다
		// Before, LessThen(LessThanEqual): 작다
		
		// List<Item> list = itemRepository.findByPriceGreaterThan(100000);
		// List<Item> list = itemRepository.findByPriceGreaterThanEqual(100000);
	
		// List<Item> list = itemRepository.findByPriceBetween(50000, 100000);
		
		// where price >= 50000 and color = 'white'
		List<Item> list = itemRepository.findByPriceGreaterThanEqualAndColor(100000, "white");
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		
		return "result";
	}
	
	@GetMapping("/m12")
	public String m12(Model model) {
	
		// isEmpty, isNull
		// isNotEmpty, isNotNull
		// - isNull > null 체크 > where tel is null
		// - isEmpty > 빈문자열, 집합(size: 0) 등을 체크
		
		// qty, description
		// List<Item> list = itemRepository.findByQtyIsNull();
		
		// List<Item> list = itemRepository.findByQtyIsNullAndDescriptionIsNull();
		
		// List<Item> list = itemRepository.findByQtyIsNullOrDescriptionIsNull();
		
		// List<Item> list = itemRepository.findByQtyIsNullOrDescriptionIsNullAndPriceGreaterThan(100000);
		
		// where (qty is null or description is null) and price > 100000
		
		
		List<Item> list = itemRepository.findByQtyIsNotNull();
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		
		return "result";
	}
	
	
	@GetMapping("/m13")
	public String m13(Model model) {
	
		// In, NotIn
		// - 열거형 조건
		// - where color in ('black', 'white')
		// - 매개변수 > List 전달
		
		List<String> colors = List.of("black", "white"); // 읽기 전용 List
		
		// List<Item> list = itemRepository.findByColorIn(colors);
		List<Item> list = itemRepository.findByColorNotIn(colors);
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		
		return "result";
	}
	
	
	@GetMapping("/m14")
	public String m14(Model model) {
	
		// StartsWith, StartingWith
		// EndsWith, EndingWith
		// Contains
		// Like
		
		// where name = '스마트폰'
		// where name like '스마트%'
		// where name like ? escape '\' 
		// List<Item> list = itemRepository.findByNameStartsWith("스마트");
		// List<Item> list = itemRepository.findByNameEndsWith("폰");
		// List<Item> list = itemRepository.findByNameContains("기");
		List<Item> list = itemRepository.findByDescriptionLike("%기능%");
				
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m15")
	public String m15(Model model) {
		
		// 정렬
		// - OrderBy컬럼명Asc
		// - OrderBy컬럼명Desc
		
		// List<Item> list = itemRepository.findAllByOrderByNameAsc();
		// List<Item> list = itemRepository.findByColorOrderByPriceAsc("white");
		List<Item> list = itemRepository.findAllByOrderByColorAscPriceDesc();

				
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m16")
	public String m16(Model model, @RequestParam("price") Integer price, @RequestParam("order") String order) {
		
		// 가격이 5만원 이상 > 내림차순
		// 동적 쿼리로 작성하고 싶을때 
		// m16?price=1000000&order=asc
		// m16?price=1000000&order=desc
		
		//List<Item> list = itemRepository.findByPriceGreaterThanEqual(price);
		
//		Direction orderDirection = Sort.Direction.ASC;
//		
//		if(order.equals("desc")) {
//			orderDirection = Sort.Direction.DESC;
//		}
		
		//List<Item> list = itemRepository.findByPriceGreaterThanEqual(Sort.by(orderDirection, "price"), price);
		
		// List<Item> list = itemRepository.findAllByOrderByPriceAsc();
		// List<Item> list = itemRepository.findAll(Sort.by("price"));
		// List<Item> list = itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
		// List<Item> list = itemRepository.findAll(Sort.by("color", "price")); //asc
		List<Item> list = itemRepository
				.findAll(
						Sort.by(
								Sort.Order.asc("color"), 
								Sort.Order.desc("price")
								)
						);
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	
	@GetMapping("/m17")
	public String m17(Model model) {
		
		// 페이징
		// PageRequest pageRequest = PageRequest.of(페이지번호, 개수);
		PageRequest pageRequest = PageRequest.of(5, 5);
		
		// List 대신 Page(페이징 관련 기능이 추가된 List) 반환
		Page<Item> list = itemRepository.findAll(pageRequest);
		
		System.out.println(list.getNumber()); // 0 > 페이지 번호
		System.out.println(list.getNumberOfElements()); // 5 > 가져온 엔티티 수
		System.out.println(list.getTotalElements()); // 29 > 총 엔티티 수
		System.out.println(list.getTotalPages()); // 6 > 총 페이지 수
		System.out.println(list.getSize()); // 5 > 규칙 pageRequest.of(0, 5)의 매개변수 5
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m18")
	public String m18(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
		
		// 페이징 구현
		// - /m18
		// - /m18?page=1
		// - /m18?page=2
		// - /m18?page=3
		page--; 
		
		PageRequest pageRequest = PageRequest.of(page, 5);
		
		// List 대신 Page(페이징 관련 기능이 추가된 List) 반환
		Page<Item> list = itemRepository.findAll(pageRequest);
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		// 페이지바
		String temp = "";
		for(int i=1; i<list.getTotalPages(); i++) {
			temp += """
					<a href="/m18?page=%d">%d</a>
					""".formatted(i, i);
		}
		
		model.addAttribute("dlist", dlist);
		model.addAttribute("temp", temp);
		
		return "result";
	}
	
	// ------------------------ Query Method > 메서드 만드는 수업 
	
	
	// ------------------------ Query Method > 엔티티 조작 > 조인
	
	@GetMapping("/m19")
	public String m19(Model model) {
		
		// DB 테이블 > 엔티티 생성
		
		/*
		 	1:1 
		 	- tblUser:tblUserInfo
		 	- User:UserInfo
		 	
		*/
		
		// User 가져오기
		
		Optional<User> user = userRepository.findById("hong");
		
		System.out.println(user.get().getName());
		System.out.println(user.get().getPw());
		//System.out.println(user.get().getUserInfo().getAddress());
		
		return "result";
	}
	
	
	@GetMapping("/m20")
	public String m20(Model model) {
		
		// DB 테이블 > 엔티티 생성
		
		/*
		 	1:1 
		 	- tblUser:tblUserInfo
		 	- User:UserInfo
		 	
		*/
		
		// UserInfo 가져오기
		UserInfo userInfo = userInfoRepository.findById("hong").get();
		
		System.out.println(userInfo.getAddress());
		System.out.println(userInfo.getGender());
		System.out.println(userInfo.getUser().getName());
		
		
		return "result";
	}
	
	@GetMapping("/m21")
	public String m21(Model model) {
		
		/*
		 	1:N
		 	- tblUser:tblBoard
		 	- User:Board
		 	
		*/
		User user = userRepository.findById("hong").get();
		
		System.out.println(user.getName());
		System.out.println(user.getBoard().size());
		
		user.getBoard().forEach(board -> {
			System.out.println(board.getSubject());
		});
		
		return "result";
	}
	
	@GetMapping("/m22")
	public String m22(Model model) {
		
		/*
		 	1:N
		 	- tblUser:tblBoard
		 	- User:Board
		 	
		*/
		Board board = boardRepository.findById(1L).get();
		
		System.out.println(board.getSubject());
		System.out.println(board.getContent());
		System.out.println(board.getUser().getName());
		
		return "result";
	}
	
	@GetMapping("/m23")
	public String m23(Model model) {
		
		/*
		 	N:N
		 	1:N N:1
		 	- tblTag:tblTagging tblTagging:tblBoard
		 	- Tag:Tagging Tagging:Board
		 	
		*/
		
		// Board 가져오기
		Board board = boardRepository.findById(10L).get();
		
		System.out.println(board.getSubject());
		System.out.println(board.getTagging().get(0).getTag().getTag());
		
		return "result";
	}
	
	
	@GetMapping("/m24")
	public String m24(Model model) {
		
		List<Board> list = boardRepository.findAll();
		
		model.addAttribute("blist", list);
		
		return "result";
	}
	
	@GetMapping("/m25")
	public String m25(Model model) {
		
		/*
			JPQL, Java Persistence Query Language
			- JPA에서 질의에 사용하는 전용 질의문(JPA 전용 SQL)
			
			SQL: 테이블을 대상으로 질의
			JPQL: 엔티티를 대상으로 질의
		
		*/
		
		// QueryMethod > X
		// 내가 직접 구현하는 메서드 > O
		// List<Item> list = itemRepository.m25();
		
		// nativeQuery
		List<Item> list = itemRepository.m25_1();
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	
	@GetMapping("/m26")
	public String m26(Model model, @RequestParam(name = "color", defaultValue = "black") String color) {
		
		// - /m26
		// - /m26?color=white
		List<Item> list = itemRepository.m26(color);
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	
	@GetMapping("/m27")
	public String m27(Model model, ItemDto dto) {
		
		// dto(color=white, price=100000)
		List<Item> list = itemRepository.m27(dto);
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m28")
	public String m28(Model model) {
		
		// JPA(ORM)
		// - DB 직접 조작(X) > 엔티티(객체)를 조작 > DB 반영
		
		// JPA 질의 방식
		// 1. Query Method
		// 2. JPQL > 엔티티를 대상으로 하는 질의(SQL)
		// 		- Query DSL > JPQL을 자바 방식으로 만들어주는 도구 
		// 3. Native Query > 오라클 SQL
		
		// Query DSL
		// - JPQL 작성을 도와주는 동적 쿼리 빌더
		// - JPQL 자바의 메서드를 사용해서 생성
		// 	- 안정성 향상(***)
		// 	- 컴파일 오류 발견(***)
		// 	- 가독성 높음
		// - 엔티티 조작을 도와주는 QClass가 필요(******)
		
		// select * from tblItem
		List<Item> list = itemQueryDSLRepository.m28();
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m29")
	public String m29(Model model) {
		
		// - /m29?name=태블릿
		
		// 레코드 1개 반환
		
		Item item = itemQueryDSLRepository.m29("마우스");
		
		model.addAttribute("dto", item.toDto());
		
		return "result";
	}
	
	@GetMapping("/m30")
	public String m30(Model model) {
		
		List<String> names = itemQueryDSLRepository.m30();
		
		model.addAttribute("names", names);
		
		return "result";
	}
	
	@GetMapping("/m31")
	public String m31(Model model) {
		
		// 다중 컬럼
		
		// 1. 모든 컬럼 > List<DTO>
		// 2. 1개 컬럼 > List<String>
		// 3. 2개 컬럼 > List<Tuple>
		
		
		List<Tuple> list = itemQueryDSLRepository.m31();
		
		model.addAttribute("tlist", list);
		
		return "result";
	}
	
	@GetMapping("/m32")
	public String m32(Model model) {
		
		// 일부 컬럼 조회 > Entity 사용 X
		// 1. Tuple
		// 2. DTO
		
		// 일부 컬럼 조회
		// 1. Entity > 편함, 모든 컬럼. 사용하지 않는 값까지 가져옴(단점)
		// 2. Tuple > 조금 편함. 컬럼 인덱스 접근
		// 3. DTO > 불편함. 프로퍼티(컬럼명) > 가독성 좋음.
		
		// select 실행
				// 1. 모든 컬럼
				// - List<Entity>
				// 2. 일부 컬럼(1개)
				// - List<String>
				// - List<Integer>
				// 3. 일부 컬럼(N개)
				// - List<Tuple>
				// - List<DTO>
				
				List<ItemDto> list = itemQueryDSLRepository.m32();
			
				model.addAttribute("dlist", list);
		
		return "result";
	}
	
	@GetMapping("/m33")
	public String m33(Model model, ItemDto dto) {
		
		// where() 절
		
		List<Item> list = itemQueryDSLRepository.m33(dto);

		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m34")
	public String m34(Model model) {
		
		// 정렬
		List<Item> list = itemQueryDSLRepository.m34();
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m35")
	public String m35(Model model
			, @RequestParam(name = "page", defaultValue = "1") Integer page) {
		
		// 페이징
		// - offset: 가져올 레코드의 시작 위치(begin)
		// - limit: 가져올 개수(size)
		
		int limit = 10;
		int offset = (page - 1) * limit;
		
		List<Item> list = itemQueryDSLRepository.m35(offset, limit);
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m36")
	public String m36(Model model) {
		
		// 집계 함수
		
		Object count = itemQueryDSLRepository.m36();
		
		System.out.println(count);
		
		return "result";
	}
	
	@GetMapping("/m37")
	public String m37(Model model) {
		
		// 그룹 바이 + 집계함수
		
		List<Tuple> list= itemQueryDSLRepository.m37();
		
		System.out.println(list);
		
		return "result";
	}
	
	@GetMapping("/m38")
	public String m38(Model model) {
		
		// 서브쿼리 (JPA)
		// - where절(O)
		// - select절(O)
		// - from 절(X) > Native Query 사용(O)
		
		// select * from tblItem where price >= (평균가격);
		List<Item> list= itemQueryDSLRepository.m38();
		
		List<ItemDto> dlist = list.stream().map(item -> item.toDto()).collect(Collectors.toList());
		
		model.addAttribute("dlist", dlist);
		
		return "result";
	}
	
	@GetMapping("/m39")
	public String m39(Model model) {
		
		// select name, price, color, (같은 색상의 평균 가격) form tblItem;
		// select name, price, color, (select avg(price) from tblItem b where a.color = b.color) from tblItem a
		
		List<Tuple> list = itemQueryDSLRepository.m39();
		
		model.addAttribute("tupleList", list);
		
		return "result";
	}
	
	@GetMapping("/m40")
	public String m40(Model model) {
		
		// join
		// - 1:1
		// - User:UserInfo
		
		User user  = userQueryDSLRepository.m40();
		
		System.out.println(user.getName());
		//System.out.println(user.getUserInfo().getAddress());
		
		return "result";
	}
	
	@GetMapping("/m41")
	public String m41(Model model) {
		
		List<User> ulist  = userQueryDSLRepository.m41();
		
		model.addAttribute("ulist", ulist);
		
		return "result";
	}
	
	@GetMapping("/m42")
	public String m42(Model model) {
		
		/*
			JPA에서의 연관 관계별 FetchType
			- @OneToOne 	 1:1 EAGER
			- @ManyToOne  N:1 EAGER
			- @OneToMany  1:N LAZY
			- @ManyToMany N:N(비권장) LAZY
			
			FetchType이란?
			1. 1개의 엔티티 find > 1개의 엔티티 사용: FetchType 무관
			2. 1개의 엔티티 find > N(자식/부모)개의 연관 엔티티 사용: FetchType 동작(***)
				2.1 LAZY
					- 1개의 엔티티를 가져올 때 그 엔티티만 가져옴
					- 연관 엔티티를 조회하는 순간 그때 가져옴
					- User:Board
						- 우선 User만 가져와라
						- 나중에 필요하면 Board를 가져와라
					- 쿼리 여러번 실행
				2.2 EAGER
					- 1개의 엔티티를 가져올 때 엔티티에 정의된 모든 관련 엔티티도 한번에 가져옴
					- User:Board
						- User와 관계된 Board까지 한번에 가져오기
						- Join 실행
			
		*/
		// User:Board
		User user = userRepository.findById("hong").get();
		
		// 1. 부모 테이블 접근
		System.out.println(user.getName());
		
		// 2. 자식 테이블 접근
		for(Board board : user.getBoard()) {
			System.out.println(board.getSubject());
		}
		
		return "result";
	}
	
	
	@GetMapping("/m43")
	public String m43(Model model) {
		
		User user = userRepository.findById("hong").get();
		
		System.out.println(user.getName());
		
		//System.out.println(user.getUserInfo().getAddress());
	
		return "result";
	}
	
	@GetMapping("/m44")
	public String m44(Model model) {
		
		Board board = boardRepository.findById(1L).get();

		System.out.println(board.getSubject());
		
		return "result";
	}
	
}
