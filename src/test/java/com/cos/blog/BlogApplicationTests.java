package com.cos.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BlogApplicationTests {

	@Test
	void 해쉬_암호화() {
		// given
		String encPassword = new BCryptPasswordEncoder().encode("1234");
		
		// when

		// then
		System.out.println("1234 해쉬:" + encPassword);
	}

}
