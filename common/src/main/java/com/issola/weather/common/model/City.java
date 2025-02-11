package com.issola.weather.common.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.issola.weather.common.dto.request_results.LocalNamesDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.List;

@Document(collection = "cities")
@Getter
public class City
{
    @Id
    private String id;

    private String name;

    private Map<String, String> localNames;

    private String lat;

    private String lon;
}
