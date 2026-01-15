package Projekt.PCCarovnik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackages = "Projekt.PCCarovnik.dao")
@ComponentScan(basePackages = "com.example")
public class PcCarovnikApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcCarovnikApplication.class, args);
	}

	@GetMapping
	public String hello() {
		return "Hello World";
	}
}
