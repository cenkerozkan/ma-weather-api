package com.issola.weather.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.issola.weather.common.dto.WeatherApiListFieldDto;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WeatherApiResultDto
{
    private Map<String, Float> coordinates;

    private List<WeatherApiListFieldDto> list;
}
