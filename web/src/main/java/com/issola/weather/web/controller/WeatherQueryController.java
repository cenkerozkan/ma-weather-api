package com.issola.weather.web.controller;

import com.issola.weather.common.dto.WeatherQueryResponseDto;
import com.issola.weather.common.repository.IWeatherQualityRepository;
import com.issola.weather.web.service.IWeatherQualityService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherQueryController
{
    private final ICityRepository ICityRepository;

    private final IWeatherQualityService weatherQualityService;


    @GetMapping("/GetWeatherQuality")
    @ResponseBody
    public ResponseEntity<WeatherQueryResponseDto> queryWeather(@RequestParam String city,
                                                                @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        WeatherQueryResponseDto response = weatherQualityService.getWeatherQuality(city, startDate, endDate);
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
