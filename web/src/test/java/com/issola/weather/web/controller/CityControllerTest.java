package com.issola.weather.web.controller;

import com.issola.weather.common.dto.CityNameDto;
import com.issola.weather.common.model.City;
import com.issola.weather.web.service.ICityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    @Mock
    private ICityService cityService;

    @InjectMocks
    private CityController cityController;

    private City testCity;
    private List<City> testCities;
    private List<CityNameDto> testCityNames;

    @BeforeEach
    void setUp() {
        Map<String, String> localNames = new HashMap<>();
        localNames.put("en", "Istanbul");
        localNames.put("tr", "İstanbul");

        testCity = City.builder()
                .name("Istanbul")
                .localNames(localNames)
                .lat("41.0082")
                .lon("28.9784")
                .build();

        testCities = List.of(testCity);
        
        testCityNames = List.of(
            CityNameDto.builder()
                .name("Istanbul")
                .build()
        );
    }

    @Test
    void getAllCities_ReturnsCities() {
        when(cityService.getAllCities()).thenReturn(testCities);

        ResponseEntity<List<City>> response = cityController.getAllCities();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCities, response.getBody());
    }

    @Test
    void getAllCities_WhenEmpty_ReturnsNoContent() {
        when(cityService.getAllCities()).thenReturn(new ArrayList<>());

        ResponseEntity<List<City>> response = cityController.getAllCities();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getCityByName_ReturnsCity() {
        when(cityService.getCityByName("Istanbul")).thenReturn(testCity);

        ResponseEntity<City> response = cityController.getCityByName("Istanbul");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCity, response.getBody());
    }

    @Test
    void getAllCityNames_ReturnsCityNames() {
        when(cityService.getAllCityNames()).thenReturn(testCityNames);

        ResponseEntity<List<CityNameDto>> response = cityController.getAllCityNames();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCityNames, response.getBody());
    }
} 