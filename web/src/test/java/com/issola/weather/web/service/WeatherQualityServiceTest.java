package com.issola.weather.web.service;

import com.issola.weather.common.dto.ResultsDatesDto;
import com.issola.weather.common.dto.WeatherQueryResponseDto;
import com.issola.weather.common.model.WeatherQuality;
import com.issola.weather.common.repository.ICityRepository;
import com.issola.weather.common.repository.IWeatherQualityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherQualityServiceTest {

    @Mock
    private ICityRepository cityRepository;

    @Mock
    private IWeatherQualityRepository weatherQualityRepository;

    @InjectMocks
    private WeatherQualityService weatherQualityService;

    private WeatherQuality testWeatherQuality;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.now().minusDays(7);
        endDate = LocalDate.now();

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