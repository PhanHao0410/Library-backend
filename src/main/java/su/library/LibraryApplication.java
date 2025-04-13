package su.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@RestController
@ComponentScan
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
