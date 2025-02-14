package com.issola.weather.web.service;

import com.issola.weather.common.dto.WeatherApiListFieldDto;
import com.issola.weather.common.dto.WeatherApiResultDto;
import com.issola.weather.common.dto.WeatherQueryResponseDto;
import com.issola.weather.common.model.City;
import com.issola.weather.common.model.WeatherQuality;
import com.issola.weather.common.repository.ICityRepository;
import com.issola.weather.common.repository.IWeatherQualityRepository;
import com.issola.weather.web.client.IOpenWeatherClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherQualityServiceTest {

    @Mock
    private ICityRepository cityRepository;

    @Mock
    private IWeatherQualityRepository weatherQualityRepository;

    @Mock
    private IOpenWeatherClient openWeatherClient;

    @InjectMocks
    private WeatherQualityService weatherQualityService;

    private City testCity;
    private WeatherQuality testWeatherQuality;
    private WeatherApiResultDto testWeatherApiResult;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.now().minusDays(7);
        endDate = LocalDate.now();

        // Setup test city
        testCity = City.builder()
                .name("Istanbul")
                .lat("41.0082")
                .lon("28.9784")
                .localNames(Map.of("en", "Istanbul"))
                .build();

        // Setup weather quality
        testWeatherQuality = WeatherQuality.builder()
                .city("Istanbul")
                .results(new ArrayList<>())
                .build();

        // Setup API result
        Map<String, Float> components = new HashMap<>();
        components.put("co", 1.0f);
        components.put("o3", 2.0f);
        components.put("so2", 3.0f);

        WeatherApiListFieldDto listField = WeatherApiListFieldDto.builder()
                .dt((int) LocalDate.now().atTime(12, 0).toEpochSecond(ZoneOffset.UTC))
                .components(components)
                .build();

        testWeatherApiResult = WeatherApiResultDto.builder()
                .list(List.of(listField))
                .build();
    }

    @Test
    void getWeatherQuality_WithValidCity_ReturnsData() {
        when(cityRepository.findByName("Istanbul")).thenReturn(testCity);
        when(cityRepository.findAll()).thenReturn(List.of(testCity));
        when(weatherQualityRepository.getWeatherQualityByCity("Istanbul")).thenReturn(testWeatherQuality);
        when(openWeatherClient.getWeatherData(anyString())).thenReturn(testWeatherApiResult);

        WeatherQueryResponseDto result = weatherQualityService.getWeatherQuality("Istanbul", startDate, endDate);

        assertNotNull(result);
        assertEquals("Istanbul", result.getCity());
    }

    @Test
    void getWeatherQuality_WithInvalidCity_ThrowsException() {
        when(cityRepository.findAll()).thenReturn(List.of(testCity));

        assertThrows(ResponseStatusException.class, () ->
            weatherQualityService.getWeatherQuality("NonExistent", startDate, endDate)
        );
    }

    @Test
    void deleteRecord_WithValidData_ReturnsTrue() {
        when(weatherQualityRepository.getWeatherQualityByCity("Istanbul")).thenReturn(testWeatherQuality);
        doNothing().when(weatherQualityRepository).updateResultsByCity(anyString(), any());

        boolean result = weatherQualityService.deleteRecord("Istanbul", startDate, endDate);

        assertTrue(result);
    }

    @Test
    void getAllAirDataByCity_WithValidCity_ReturnsData() {
        when(cityRepository.findAll()).thenReturn(List.of(testCity));
        when(weatherQualityRepository.getWeatherQualityByCity("Istanbul")).thenReturn(testWeatherQuality);

        WeatherQueryResponseDto result = weatherQualityService.getAllAirDataByCity("Istanbul");

        assertNotNull(result);
        assertEquals("Istanbul", result.getCity());
    }

    @Test
    void getAllAirDataByCity_WithInvalidCity_ThrowsException() {
        when(cityRepository.findAll()).thenReturn(List.of(testCity));

        assertThrows(ResponseStatusException.class, () ->
            weatherQualityService.getAllAirDataByCity("NonExistent")
        );
    }
} 