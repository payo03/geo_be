package com.spring.geo_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GeoBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoBeApplication.class, args);
	}

}
