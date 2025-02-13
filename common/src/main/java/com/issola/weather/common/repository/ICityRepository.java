package com.issola.weather.common.repository;

import com.issola.weather.common.model.WeatherQuality;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.issola.weather.common.model.City;
import com.issola.weather.common.dto.CityNameDto;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICityRepository extends MongoRepository<City, String>
{
    City findByName(String name);

    @Query("{ 'name' : ?0 }")
    List<City> findCityByName(String name);

    @Query
    City findByLocalNames(String name);

    @Query(value = "{}", fields = "{ name: 1, _id: 0 }")
    List<String> getAllCityNames();
}
