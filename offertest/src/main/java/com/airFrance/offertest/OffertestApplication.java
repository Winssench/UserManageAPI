package com.airFrance.offertest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.airFrance.offertest.model.UserModel;
import com.airFrance.offertest.repository.UserRepository;

@SpringBootApplication
public class OffertestApplication {
	
	@Autowired
	UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(OffertestApplication.class, args);
	}

	


}
