package com.issola.weather.web.controller;

import com.issola.weather.common.dto.ResultsDto;
import com.issola.weather.common.dto.WeatherQueryResponseDto;
import com.issola.weather.web.service.IWeatherQualityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherQueryControllerTest {

    @Mock
    private IWeatherQualityService weatherQualityService;

    @InjectMocks
    private WeatherQueryController weatherQueryController;

    private WeatherQueryResponseDto testResponse;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.now().minusDays(7);
        endDate = LocalDate.now();

        List<Map<String, String>> categories = List.of(
            Map.of("co", "GOOD"),
            Map.of("o3", "MODERATE"),
            Map.of("so2", "GOOD")
        );

        ResultsDto resultsDto = ResultsDto.builder()
                .date(LocalDate.now())
                .categories(categories)
                .build();

        testResponse = WeatherQueryResponseDto.builder()
                .city("Istanbul")
                .results(List.of(resultsDto))
                .build();
    }

    @Test
    void queryWeather_ReturnsWeatherData() {
        when(weatherQualityService.getWeatherQuality("Istanbul", startDate, endDate))
                .thenReturn(testResponse);

        ResponseEntity<WeatherQueryResponseDto> response = 
                weatherQueryController.queryWeather("Istanbul", startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testResponse, response.getBody());
    }

    @Test
    void deleteRecord_WhenSuccessful_ReturnsOk() {
        when(weatherQualityService.deleteRecord("Istanbul", startDate, endDate))
                .thenReturn(true);

        ResponseEntity<Map<String, String>> response = 
                weatherQueryController.deleteRecord("Istanbul", startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record deleted successfully", response.getBody().get("message"));
    }

    @Test
    void deleteRecord_WhenNotFound_ReturnsNotFound() {
        when(weatherQualityService.deleteRecord("Istanbul", startDate, endDate))
                .thenReturn(false);

        ResponseEntity<Map<String, String>> response = 
                weatherQueryController.deleteRecord("Istanbul", startDate, endDate);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Record not found", response.getBody().get("message"));
    }

    @Test
    void getAllAirDataByCity_ReturnsWeatherData() {
        when(weatherQualityService.getAllAirDataByCity("Istanbul"))
                .thenReturn(testResponse);

        ResponseEntity<WeatherQueryResponseDto> response = 
                weatherQueryController.getAllAirDataByCity("Istanbul");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testResponse, response.getBody());
    }
} 