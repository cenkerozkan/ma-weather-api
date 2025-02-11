package com.issola.weather.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "air_quality")
public class WeatherQuality
{
    @Id
    private String id;
}
