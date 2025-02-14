package com.issola.weather.web.service;

import com.issola.weather.common.dto.CityNameDto;
import com.issola.weather.common.model.City;
import com.issola.weather.common.repository.ICityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private ICityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    private City testCity;
    private List<City> testCities;

    @BeforeEach
    void setUp() {
        Map<String, String> localNames = new HashMap<>();
        localNames.put("en", "Istanbul");
        localNames.put("tr", "İstanbul");

        testCity = City.builder()
                .name("Istanbul")
                .lat("41.0082")
                .lon("28.9784")
                .localNames(localNames)
                .build();

        testCities = List.of(testCity);
    }

    @Test
    void getAllCities_ReturnsCities() {
        when(cityRepository.findAll()).thenReturn(testCities);

        List<City> result = cityService.getAllCities();

        assertEquals(testCities, result);
    }

    @Test
    void getCityByName_ReturnsCity() {
        when(cityRepository.findByName("Istanbul")).thenReturn(testCity);

        City result = cityService.getCityByName("Istanbul");

        assertEquals(testCity, result);
    }

    @Test
    void getCityByName_WhenNotFound_ThrowsException() {
        when(cityRepository.findByName("NonExistent")).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> 
            cityService.getCityByName("NonExistent")
        );
    }

    @Test
    void getCityByLocalNames_ReturnsCity() {
        when(cityRepository.findAll()).thenReturn(testCities);

        City result = cityService.getCityByLocalNames("İstanbul");

        assertEquals(testCity, result);
    }

    @Test
    void getCityByLocalNames_WhenNotFound_ReturnsNull() {
        when(cityRepository.findAll()).thenReturn(testCities);

        City result = cityService.getCityByLocalNames("NonExistent");

        assertNull(result);
    }

    @Test
    void getAllCityNames_ReturnsCityNames() {
        when(cityRepository.findAll()).thenReturn(testCities);

        List<CityNameDto> result = cityService.getAllCityNames();

        assertFalse(result.isEmpty());
        assertEquals("Istanbul", result.get(0).getName());
    }
} 