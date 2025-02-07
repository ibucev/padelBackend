package com.example.padel;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.padel.model.Role;
import com.example.padel.repository.RoleRepository;

@SpringBootApplication
@EnableAsync
public class PadelApplication {

	public static void main(String[] args) {
		SpringApplication.run(PadelApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("PLAYER").isEmpty()) {
				roleRepository.save(Role.builder()
					.name("PLAYER")
					.creationDate(LocalDate.now()).build());
			}

		};
	}

}
