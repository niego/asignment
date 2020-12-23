package com.scrapper.asignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.scrapper.asignment.controller","com.scrapper.asignment.service"})
public class AsignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsignmentApplication.class, args);
	}

}
