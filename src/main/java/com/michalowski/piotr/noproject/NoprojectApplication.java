package com.michalowski.piotr.noproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.michalowski.piotr.noproject")
public class NoprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoprojectApplication.class, args);
	}
}
