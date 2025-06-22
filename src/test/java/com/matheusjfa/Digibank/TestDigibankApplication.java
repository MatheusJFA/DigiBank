package com.matheusjfa.Digibank;

import org.springframework.boot.SpringApplication;

public class TestDigibankApplication {

	public static void main(String[] args) {
		SpringApplication.from(DigibankApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
