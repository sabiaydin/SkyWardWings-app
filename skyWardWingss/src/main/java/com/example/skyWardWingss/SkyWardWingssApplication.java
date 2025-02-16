package com.example.skyWardWingss;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SkyWardWingssApplication {
	public static void main(String[] args) {
		SpringApplication.run(SkyWardWingssApplication.class, args);
	}
}
