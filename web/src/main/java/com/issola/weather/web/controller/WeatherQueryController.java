package com.issola.weather.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/weather")
public class WeatherQueryController
{
    @GetMapping("/QueryWeather")
    public ResponseEntity<Map<String, String>> queryWeather()
    {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Query Weather!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/DeleteRecord")
    public ResponseEntity<Map<String, String>> deleteRecord()
    {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Delete Record!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
