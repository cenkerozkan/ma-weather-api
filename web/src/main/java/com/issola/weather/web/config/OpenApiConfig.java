package com.issola.weather.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI weatherApiDoc() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setEmail("your.email@example.com");
        contact.setName("API Support");

        Info info = new Info()
                .title("Weather API")
                .version("1.0.0")
                .description("API for querying weather data and air quality information")
                .contact(contact);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
} 