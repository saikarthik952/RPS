package com.multiplayer.RPS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpsApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RpsApplication.class);
		app.run(args);
	}
}
