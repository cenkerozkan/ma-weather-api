package com.issola.weather.common.repository;

import com.issola.weather.common.dto.ResultsDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.issola.weather.common.model.WeatherQuality;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IWeatherQualityRepository extends MongoRepository<WeatherQuality, String>
{
    @Query("{ 'city': ?0}")
    WeatherQuality getWeatherQualityByCity(String city);

    @Query("{ 'city': ?0 }")
    @Update("{ '$set': { 'results': ?1 }}")
    void updateResultsByCity(String city, List<ResultsDto> results);
}
