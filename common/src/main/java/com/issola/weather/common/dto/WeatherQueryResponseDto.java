package com.issola.weather.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WeatherQueryResponseDto
{
    private String city;

    private List<ResultsDto> results;
}
