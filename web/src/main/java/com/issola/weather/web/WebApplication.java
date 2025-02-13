package com.issola.weather.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestOperations;;

@SpringBootApplication
@ComponentScan("com.issola.weather")
@EnableMongoRepositories("com.issola.weather.common.repository")
public class WebApplication
{
    @Bean
    public RestOperations restTemplate(RestTemplateBuilder builder)
    {
        return builder.build();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(WebApplication.class, args);
    }
}
