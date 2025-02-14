package com.issola.weather.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HelloWorldControllerTest {

    private final HelloWorldController controller = new HelloWorldController();

    @Test
    void helloWorld_ReturnsCorrectMessage() {
        ResponseEntity<Map<String, String>> response = controller.helloWorld();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hello World!", response.getBody().get("message"));
    }
} 