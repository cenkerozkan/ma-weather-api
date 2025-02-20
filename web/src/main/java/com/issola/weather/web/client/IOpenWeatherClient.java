package com.issola.weather.web.client;

import com.issola.weather.common.dto.WeatherApiResultDto;


public interface IOpenWeatherClient
{
    WeatherApiResultDto getWeatherData(String lat, String lon, long startDateEpoch, long endDateEpoch, String formattedCity);
}
