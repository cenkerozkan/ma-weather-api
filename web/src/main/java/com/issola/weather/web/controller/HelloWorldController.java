package com.issola.weather.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController
{
    @GetMapping("/HelloWorld")
    @ResponseBody
    public ResponseEntity<Map<String, String>> helloWorld()
    {
        // Best way to return a JSON response I think.
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello World!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
