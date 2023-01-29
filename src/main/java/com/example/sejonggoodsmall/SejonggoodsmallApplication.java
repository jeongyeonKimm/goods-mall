package com.example.sejonggoodsmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SejonggoodsmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(SejonggoodsmallApplication.class, args);
	}

}
