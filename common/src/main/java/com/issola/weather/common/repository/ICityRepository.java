package com.issola.weather.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.issola.weather.common.model.City;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityRepository extends MongoRepository<City, String>
{
}
