package com.michalowski.piotr.noproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.michalowski.piotr.noproject")
public class NoprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoprojectApplication.class, args);
	}
}
