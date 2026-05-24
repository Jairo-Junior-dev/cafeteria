package com.cafeteria.cafeteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class CafeteriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeteriaApplication.class, args);
	}

}
