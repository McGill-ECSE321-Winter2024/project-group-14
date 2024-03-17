package ca.mcgill.ecse321.sportCenterRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author:Stephen Huang and
 */

@RestController
@SpringBootApplication
public class SportCenterRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportCenterRegistrationApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting() {
		return "Hello world!";
	}

}
