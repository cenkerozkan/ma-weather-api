package com.issola.weather.common.dto.request_results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GeocodingDto {
    private String name;

    private LocalNamesDto local_names;

    private double lat;

    private double lon;

    private String country;

    private String state;
}