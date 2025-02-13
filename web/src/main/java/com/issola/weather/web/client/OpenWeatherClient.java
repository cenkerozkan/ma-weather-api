package com.issola.weather.web.client;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.issola.weather.web.util.IHttpRequestExecutor;
import com.issola.weather.common.dto.WeatherApiResultDto;

@RequiredArgsConstructor
@Service
public class OpenWeatherClient implements IOpenWeatherClient
{
    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherClient.class);

    private final IHttpRequestExecutor httpRequestExecutor;

    @Value("${openweather.api.key}")
    private String apiKey;

    private String buildUrl(String url)
    {
        String finalUrl = url + "&appid=" + apiKey;
        logger.info("Final URL: " + finalUrl);
        return finalUrl;
    }


    @Override
    public WeatherApiResultDto getWeatherData(String url)
    {

        return httpRequestExecutor.executeGetRequest(buildUrl(url), WeatherApiResultDto.class);
    }
}
