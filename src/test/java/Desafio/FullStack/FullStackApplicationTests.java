package Desafio.FullStack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FullStackApplicationTest {

	@Autowired
	private FullStackApplication fullStackApplication;

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> fullStackApplication.main(new String[]{}));
	}
}
