package com.issola.weather.common.model;

import com.issola.weather.common.dto.ResultsDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "air_quality")
@AllArgsConstructor
@Builder
public class WeatherQuality
{
    @Id
    private String id;

    @Field("city")
    private String city;

    @Field("results")
    private List<ResultsDto> results;
}