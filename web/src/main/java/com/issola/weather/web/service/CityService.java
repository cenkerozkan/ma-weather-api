package com.issola.weather.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.issola.weather.common.model.City;
import com.issola.weather.common.dto.CityNameDto;
import com.issola.weather.common.repository.ICityRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class CityService implements ICityService
{
    private final ICityRepository ICityRepository;

    @Override
    public List<City> getAllCities()
    {
        List<City> cities = ICityRepository.findAll();

        return cities;
    }

    @Override
    public City getCityByName(String name)
    {
        City city = ICityRepository.findByName(name);

        if (city == null)
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return city;
    }

    @Override
    public City getCityByLocalNames(String name)
    {
        List<City> cities = ICityRepository.findAll();

        // Nice way to do for loops in java
        for (City city : cities)
        {
            Map<String, String> localNames = city.getLocalNames();

            if (localNames.containsValue(name))
            {
                return city;
            }
        }
        return null;
    }

    @Override
    public List<CityNameDto> getAllCityNames()
    {
        List<City> cities = ICityRepository.findAll();

        List<CityNameDto> cityNames = new ArrayList<>();

        for (City city : cities)
        {
            String name = city.getLocalNames().get("en");
            CityNameDto cityNameDto = new CityNameDto(name);
            cityNames.add(cityNameDto);
        }
        return cityNames;
    }
}