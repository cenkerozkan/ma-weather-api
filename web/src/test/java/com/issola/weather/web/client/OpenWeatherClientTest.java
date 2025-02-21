package com.issola.weather.web.client;

import com.issola.weather.common.dto.WeatherApiListFieldDto;
import com.issola.weather.common.dto.WeatherApiResultDto;
import com.issola.weather.web.util.IHttpRequestExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherClientTest {

    @Mock
    private IHttpRequestExecutor httpRequestExecutor;

    @InjectMocks
    private OpenWeatherClient openWeatherClient;

    private WeatherApiResultDto expectedResult;
    private final String API_KEY = "test-api-key";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(openWeatherClient, "apiKey", API_KEY);
        
        // Create test data
        Map<String, Float> components = new HashMap<>();
        components.put("co", 1.0f);
        components.put("o3", 2.0f);
        components.put("so2", 3.0f);

        WeatherApiListFieldDto listField = WeatherApiListFieldDto.builder()
                .dt(1234567890)
                .components(components)
                .build();

        expectedResult = WeatherApiResultDto.builder()
                .list(List.of(listField))
                .build();
    }

    @Test
    void getWeatherData_ReturnsWeatherApiResult() {
        String lat = "41.0082";
        String lon = "28.9784";
        long startDateEpoch = 1234567890L;
        long endDateEpoch = 1234567899L;
        String city = "Istanbul";
        
        String expectedUrl = String.format(
            "http://api.openweathermap.org/data/2.5/air_pollution/history?lat=%s&lon=%s&start=%d&end=%d&appid=%s",
            lat, lon, startDateEpoch, endDateEpoch, API_KEY
        );

        when(httpRequestExecutor.executeGetRequest(eq(expectedUrl), eq(WeatherApiResultDto.class)))
                .thenReturn(expectedResult);

        WeatherApiResultDto result = openWeatherClient.getWeatherData(lat, lon, startDateEpoch, endDateEpoch, city);

        assertEquals(expectedResult, result);
    }
} 