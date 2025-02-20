package com.issola.weather.web.client;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.issola.weather.web.util.IHttpRequestExecutor;
import com.issola.weather.common.dto.WeatherApiResultDto;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class OpenWeatherClient implements IOpenWeatherClient
{
    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherClient.class);

    private final IHttpRequestExecutor httpRequestExecutor;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Override
    public WeatherApiResultDto getWeatherData(String lat, String lon, long startDateEpoch, long endDateEpoch, String formattedCity)
    {
        String url = String.format("http://api.openweathermap.org/data/2.5/air_pollution/history?lat=%s&lon=%s&start=%d&end=%d&appid=%s", lat, lon, startDateEpoch, endDateEpoch, apiKey);
        try
        {
            logger.info("Getting weather data from OpenWeather API");
            return httpRequestExecutor.executeGetRequest(url, WeatherApiResultDto.class);
        }
        catch (Exception e)
        {
            logger.error("Error while getting weather data from OpenWeather API: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting weather data from OpenWeather API");
        }
    }
}
