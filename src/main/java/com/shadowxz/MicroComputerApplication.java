package com.shadowxz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.shadowxz.dao","com.shadowxz.controller","com.shadowxz.service"})
public class MicroComputerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroComputerApplication.class, args);
	}
}
