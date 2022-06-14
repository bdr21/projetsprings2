package com.miola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalTime;


@SpringBootApplication
public class Projets2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Projets2Application.class, args);
		System.out.println("==> " + LocalTime.now());
	}

}
