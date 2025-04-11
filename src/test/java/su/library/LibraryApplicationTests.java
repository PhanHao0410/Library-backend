package su.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
class LibraryApplicationTests {

	@Test
	void contextLoads() {
	}

}

// / check tạo fake cho test
// @SpringBootTest
// @ImportAutoConfiguration(exclude = {
// org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class,
// org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class
// })
// class LibraryApplicationTests {
// @Test
// void contextLoads() {
// }
// }
