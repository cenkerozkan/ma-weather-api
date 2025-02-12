package com.issola.weather.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.issola.weather.common.repository.ICityRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class WeatherQualityService implements IWeatherQualityService
{
    private final ICityRepository cityRepository;
}
