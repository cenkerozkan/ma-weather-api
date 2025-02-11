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
public class LocalNamesDto {
    private Map<String, String> local_names;
}
