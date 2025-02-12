package com.issola.weather.web.controller;

import com.issola.weather.common.dto.CityNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.issola.weather.common.model.City;
import com.issola.weather.web.service.ICityService;

import java.util.List;

@RestController
@RequestMapping("/api/city")
@RequiredArgsConstructor
public class CityController
{
    private final ICityService cityService;

    @GetMapping("/GetAllCities")
    public ResponseEntity<List<City>> getAllCities()
    {
        List<City> cities = cityService.getAllCities();

        if (cities.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/GetCityByName")
    @ResponseBody
    public ResponseEntity<City> getCityByName(@RequestParam(defaultValue = "Ankara") String name)
    {
        City city = cityService.getCityByName(name);

        if (city == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @GetMapping("/GetCityByLocalNames")
    @ResponseBody
    public ResponseEntity<City> getCityByLocalNames(@RequestParam(defaultValue = "Ankara") String name)
    {
        City city = cityService.getCityByLocalNames(name);

        if (city == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @GetMapping("/GetAllCityNames")
    @ResponseBody
    public ResponseEntity<List<CityNameDto>> getAllCityNames()
    {
        List<CityNameDto> cityNames = cityService.getAllCityNames();

        if (cityNames.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cityNames, HttpStatus.OK);
    }
}
