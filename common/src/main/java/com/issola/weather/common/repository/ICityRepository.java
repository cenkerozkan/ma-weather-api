package com.issola.weather.common.repository;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.issola.weather.common.model.City;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.ReturnedType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityRepository extends MongoRepository<City, String>
{
    @ExistsQuery("{ 'name' : ?0 }")
    boolean isCityExistsByName(String name);
    
    City findByName(String name);

    @Query("{ 'name' : ?0 }")
    List<City> findCityByName(String name);

    @Query
    City findByLocalNames(String name);

    @Query(value = "{}", fields = "{ name: 1, _id: 0 }")
    List<String> getAllCityNames();
}
