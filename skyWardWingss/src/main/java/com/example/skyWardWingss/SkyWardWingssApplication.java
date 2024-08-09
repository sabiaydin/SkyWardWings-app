package com.example.skyWardWingss;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class SkyWardWingssApplication/* implements CommandLineRunner*/ {
//	private final BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SkyWardWingssApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println(passwordEncoder.encode("sabina"));
//	}
}
