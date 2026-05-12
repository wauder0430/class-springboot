package com.test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DBTests {
	
	@Autowired
	private DataSource dataSource;

	@Test
	void test() throws SQLException {
		
		assertNotNull(dataSource);
		
		Connection conn = dataSource.getConnection();
		
		assertEquals(false, conn.isClosed());
		
	}


}
