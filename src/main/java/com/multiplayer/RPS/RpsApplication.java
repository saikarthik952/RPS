package com.multiplayer.RPS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class RpsApplication {

	public static void main(String[] args) {
		ReactorDebugAgent.init();
		ReactorDebugAgent.processExistingClasses();
		SpringApplication app = new SpringApplication(RpsApplication.class);
		app.run(args);
	}
}
