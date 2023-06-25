package com.jr_devs.assemblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class AssemblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssemblogApplication.class, args);
	}
}
