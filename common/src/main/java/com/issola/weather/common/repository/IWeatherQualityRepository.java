package com.issola.weather.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.issola.weather.common.model.WeatherQuality;

import java.util.List;

@Repository
public interface IWeatherQualityRepository extends MongoRepository<WeatherQuality, String>
{
}
