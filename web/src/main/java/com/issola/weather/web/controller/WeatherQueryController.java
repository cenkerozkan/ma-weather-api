package com.issola.weather.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.issola.weather.common.model.City;
import com.issola.weather.common.repository.ICityRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherQueryController
{
    private final ICityRepository ICityRepository;


    @GetMapping("/GetWeatherQuality")
    @ResponseBody
    public ResponseEntity<Map<String, Map<String, String>>> queryWeather(@RequestParam String city,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        Map<String, String> dates = new HashMap<>();
        dates.put("startDate", startDate.toString());
        dates.put("endDate", endDate.toString());

        Map <String, Map<String, String>> response = new HashMap<>();
        response.put("dates", dates);
        response.put("city", Map.of("name", city));

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
