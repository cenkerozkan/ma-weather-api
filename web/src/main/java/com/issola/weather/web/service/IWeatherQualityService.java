package com.issola.weather.web.service;

import com.issola.weather.common.dto.WeatherQueryResponseDto;
import com.issola.weather.common.model.City;

import java.time.LocalDate;

public interface IWeatherQualityService
{
    WeatherQueryResponseDto getWeatherQuality(String city, LocalDate startDate, LocalDate endDate);
}
