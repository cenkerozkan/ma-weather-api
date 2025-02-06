package com.issola.weather.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.coyote.Response;
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
    public Map<String, String> helloWorld()
    {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello World!");
        return response;
    }
}
