package com.issola.weather.web.service;

import com.issola.weather.common.model.City;
import com.issola.weather.common.dto.CityNameDto;

import java.util.List;

public interface ICityService
{
    List<City> getAllCities();

    City getCityByName(String name);

    City getCityByLocalNames(String name);

    List<CityNameDto> getAllCityNames();
}
