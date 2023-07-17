package com.example.automobilerestapiapp;

import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.mappers.AutomobileMapper;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Model;
import com.example.automobilerestapiapp.services.AutomobileService;
import com.example.automobilerestapiapp.services.ModelService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutomobileRestApiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomobileRestApiAppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(AutomobileService automobileService, ModelService modelService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<StoreAutomobileRequest>> typeReference = new TypeReference<>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");
			try {
				List<StoreAutomobileRequest> dtos = mapper.readValue(inputStream,typeReference);
				List<Automobile> automobiles = new ArrayList<>();
				dtos.forEach(dto -> {
					LocalDate dateOfCreation = automobileService.validateDateOfCreation(dto.getDateOfCreation());
					Model model = modelService.getModelEntity(dto.getModelId());
					Automobile automobile = AutomobileMapper.fromStoreAutomobileRequest(dto,dateOfCreation, model);
					automobiles.add(automobile);
				});
				automobileService.insertAll(automobiles);
				System.out.println("Success");
			} catch (IOException e){
				System.out.println("Failure: " + e.getMessage());
			}
		};
	}
}