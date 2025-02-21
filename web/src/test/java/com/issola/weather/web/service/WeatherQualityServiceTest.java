package com.issola.weather.web.service;

import com.issola.weather.common.dto.ResultsDatesDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

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
        // Mock city validation
        when(cityRepository.isCityExistsByName(anyString())).thenReturn(true);
        
        // Mock weather quality repository
        when(weatherQualityRepository.getWeatherQualityByCity(anyString())).thenReturn(testWeatherQuality);
        
        // Mock findMissingDates
        ResultsDatesDto resultsDatesDto = new ResultsDatesDto(new ArrayList<>());
        when(weatherQualityRepository.findMissingDates(
            anyString(), 
            any(LocalDate.class), 
            any(LocalDate.class)
        )).thenReturn(resultsDatesDto);

        WeatherQueryResponseDto result = weatherQualityService.getWeatherQuality("Istanbul", startDate, endDate);

        assertNotNull(result);
        assertEquals("Istanbul", result.getCity());
    }

    @Test
    void getWeatherQuality_WithInvalidCity_ThrowsException() {
        when(cityRepository.isCityExistsByName(anyString())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () ->
            weatherQualityService.getWeatherQuality("nonexistent", startDate, endDate)
        );
    }

    @Test
    void deleteRecord_WithValidData_ReturnsTrue() {
        when(cityRepository.isCityExistsByName(anyString())).thenReturn(true);
        when(weatherQualityRepository.getWeatherQualityByCity(anyString())).thenReturn(testWeatherQuality);
        doNothing().when(weatherQualityRepository).updateResultsByCity(anyString(), any());

        boolean result = weatherQualityService.deleteRecord("Istanbul", startDate, endDate);

        assertTrue(result);
    }
} 