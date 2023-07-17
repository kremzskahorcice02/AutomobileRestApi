package com.example.automobilerestapiapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
      return new OpenAPI()
          .info(new Info().title("Automobile Data API")
              .description("Api provides endpoints to perform basic CRUD operations three related models."
                  + " These include car producers, models of cars from those producers and automobiles"
                  + " of these models that were manufactured.")
              .version("1.0"));
    }
}
