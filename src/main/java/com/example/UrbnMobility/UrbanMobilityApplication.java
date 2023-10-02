package com.example.UrbnMobility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.UrbnMobility")
public class UrbanMobilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbanMobilityApplication.class, args);
	}
}

