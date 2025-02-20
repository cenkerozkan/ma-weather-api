package com.issola.weather.common.repository;

import com.issola.weather.common.dto.ResultsDatesDto;
import com.issola.weather.common.dto.ResultsDto;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.issola.weather.common.model.WeatherQuality;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IWeatherQualityRepository extends MongoRepository<WeatherQuality, String>
{
    @Aggregation(pipeline = {
            "{ $match: { 'city': ?0 } }",
            "{ $project: { 'city': 1, 'results': { $sortArray: { input: '$results', sortBy: { 'date': 1 } } } } }"
    })
    WeatherQuality getWeatherQualityByCity(String city);

    @Aggregation({
            "{ $facet: { 'cityData': [{ $match: { 'city': ?0 } }] } }",
            "{ $project: { 'hasCity': { $gt: [{ $size: '$cityData' }, 0] }, 'cityData': { $arrayElemAt: ['$cityData', 0] } } }",
            "{ $project: { 'hasCity': 1, 'existingDates': { $cond: { if: '$hasCity', then: { $map: { input: { $filter: { input: '$cityData.results', as: 'result', cond: { $and: [ { $gte: ['$$result.date', ?1] }, { $lte: ['$$result.date', ?2] } ] } } }, as: 'filteredResult', in: '$$filteredResult.date' } }, else: [] } } } }",
            "{ $addFields: { 'dayCount': { $ceil: { $divide: [{ $subtract: [{ $toDate: ?2 }, { $toDate: ?1 }] }, 86400000 ] } } } }",
            "{ $addFields: { 'dayCount': { $add: ['$dayCount', 1] } } }",
            "{ $addFields: { 'allDates': { $map: { input: { $range: [0, '$dayCount'] }, as: 'day', in: { $add: [{ $toDate: ?1 }, { $multiply: ['$$day', 86400000] }] } } } } }",
            "{ $project: { 'dates': { $filter: { input: '$allDates', as: 'date', cond: { $not: [{ $in: ['$$date', '$existingDates'] }] } } } } }"
    })
    ResultsDatesDto findMissingDates(String name, LocalDate startDate, LocalDate endDate);


    @Query("{ 'city': ?0 }")
    @Update("{ '$set': { 'results': ?1 }}")
    void updateResultsByCity(String city, List<ResultsDto> results);
}
