package com.issola.weather.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.issola.weather.common.model.City;
import com.issola.weather.common.repository.ICityRepository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherQueryController
{
    private final ICityRepository ICityRepository;


    @GetMapping("/QueryWeather")
    public ResponseEntity<Map<String, String>> queryWeather()
    {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Query Weather!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/GetAllCities")
    public ResponseEntity<List<City>> getAllCities()
    {
        List<City> cities = ICityRepository.findAll();

        if (cities.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @DeleteMapping("/DeleteRecord")
    public ResponseEntity<Map<String, String>> deleteRecord()
    {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Delete Record!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
