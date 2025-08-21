package com.example.spring_boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public CommandLineRunner runner(PasswordEncoder passwordEncoder) {
		return args -> {
			String rawPassword = "007272";
			String encodedPassword = passwordEncoder.encode(rawPassword);
			System.out.println("007272 のハッシュ値: " + encodedPassword);
			System.out.println("（注意: このハッシュ値は毎回変わります）");
		};
	}
}
