package com.issola.weather.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResultsDto
{
    private LocalDate Date;

    private List<Map<String, String>> Categories;
}
