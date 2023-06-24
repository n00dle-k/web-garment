package com.garderob.web_garderob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class WebGarderobApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebGarderobApplication.class, args);
	}

}
