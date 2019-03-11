package de.beach.bvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = { "de.beach.bvp" })
@EntityScan(basePackages = { "de.beach.bvp" })
public class BvPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BvPortalApplication.class, args);
	}
}