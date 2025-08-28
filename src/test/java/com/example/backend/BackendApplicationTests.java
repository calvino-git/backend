package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@TestPropertySource(properties = {"JWT_SECRET=testValue"})
@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
