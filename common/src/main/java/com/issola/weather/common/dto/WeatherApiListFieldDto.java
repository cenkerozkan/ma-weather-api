package com.issola.weather.common.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WeatherApiListFieldDto
{
    private Map<String, Integer> main;

    private Map<String, Float> components;

    private Integer dt;
}
