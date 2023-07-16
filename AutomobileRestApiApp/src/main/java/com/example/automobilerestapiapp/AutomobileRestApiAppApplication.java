package com.example.automobilerestapiapp;

import com.example.automobilerestapiapp.models.Producer;
import com.example.automobilerestapiapp.repositories.ProducerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutomobileRestApiAppApplication {

	public ProducerRepository producerRepository;

	@Autowired

	public AutomobileRestApiAppApplication(ProducerRepository producerRepository) {
		this.producerRepository = producerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AutomobileRestApiAppApplication.class, args);
	}
}