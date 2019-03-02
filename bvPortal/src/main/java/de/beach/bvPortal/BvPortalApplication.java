package de.beach.bvPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"de.beach.bvPortal"})
@EntityScan( basePackages = {"de.beach.bvPortal"} )
public class BvPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BvPortalApplication.class, args);
	}

}