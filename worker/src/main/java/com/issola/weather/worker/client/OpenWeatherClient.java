package com.issola.weather.worker.client;

import lombok.RequiredArgsConstructor;

import com.issola.weather.worker.util.IHttpRequestExecutor;
import com.issola.weather.common.dto.WeatherApiResultDto;

@RequiredArgsConstructor
public class OpenWeatherClient implements IOpenWeatherClient
{
    private final IHttpRequestExecutor httpRequestExecutor;

    @Override
    public WeatherApiResultDto getWeatherData(String url)
    {
        return httpRequestExecutor.executeGetRequest(url, WeatherApiResultDto.class);
    }
}
