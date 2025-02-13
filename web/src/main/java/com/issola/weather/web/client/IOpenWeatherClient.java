package com.issola.weather.web.client;

import com.issola.weather.common.dto.WeatherApiResultDto;


public interface IOpenWeatherClient
{
    WeatherApiResultDto getWeatherData(String url);
}
