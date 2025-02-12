package com.issola.weather.worker.client;

import com.issola.weather.common.dto.WeatherApiResultDto;


public interface IOpenWeatherClient
{
    WeatherApiResultDto getWeatherData(String url);
}
