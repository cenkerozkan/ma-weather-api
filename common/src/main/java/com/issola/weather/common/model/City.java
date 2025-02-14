package com.issola.weather.common.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.issola.weather.common.dto.LocalNamesDto;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.List;

@Document(collection = "cities")
@Getter
@Builder
public class City
{
    @Id
    private String id;

    private String name;

    @Field("local_names")
    private Map<String, String> localNames;

    private String lat;

    private String lon;
}
