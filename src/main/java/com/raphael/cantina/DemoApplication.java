package com.raphael.cantina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		System.setProperty("server.servlet.context-path", "/geces");
		SpringApplication.run(DemoApplication.class, args);
	}

}
