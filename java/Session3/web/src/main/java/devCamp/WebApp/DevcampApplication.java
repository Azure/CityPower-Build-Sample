package devCamp.WebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DevcampApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevcampApplication.class, args);
	}
}
