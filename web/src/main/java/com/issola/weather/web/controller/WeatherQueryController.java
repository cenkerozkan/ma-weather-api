package com.issola.weather.web.controller;

import com.issola.weather.common.dto.WeatherQueryResponseDto;
import com.issola.weather.web.service.IWeatherQualityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherQueryController
{
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
    public ResponseEntity<Map<String, String>> deleteRecord(@RequestParam String city,
                                                            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        boolean isDeleted = weatherQualityService.deleteRecord(city, startDate, endDate);

        if (isDeleted)
        {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Record deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
        {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Record not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/GetAllAirDataByCity")
    public ResponseEntity<WeatherQueryResponseDto> getAllAirDataByCity(@RequestParam String city)
    {
        WeatherQueryResponseDto response = weatherQualityService.getAllAirDataByCity(city);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
