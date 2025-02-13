package com.issola.weather.web.service;

import com.issola.weather.common.dto.WeatherApiListFieldDto;
import com.issola.weather.common.dto.WeatherApiResultDto;
import com.issola.weather.common.model.WeatherQuality;
import com.issola.weather.common.repository.IWeatherQualityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.issola.weather.common.dto.WeatherQueryResponseDto;
import com.issola.weather.common.dto.ResultsDto;
import com.issola.weather.common.dto.AirSubstanceDto;
import com.issola.weather.common.model.City;
import com.issola.weather.common.repository.ICityRepository;
import com.issola.weather.web.client.IOpenWeatherClient;
import com.issola.weather.common.categories.SO2PollutantCategories;
import com.issola.weather.common.categories.COPollutantCategories;
import com.issola.weather.common.categories.O3PollutantCategories;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class WeatherQualityService implements IWeatherQualityService
{
    private static final Logger logger = LoggerFactory.getLogger(WeatherQualityService.class);
    private final ICityRepository cityRepository;
    private final IWeatherQualityRepository weatherQualityRepository;
    private final IOpenWeatherClient openWeatherClient;

    private String capitalize(String str)
    {
        if (str == null || str.isEmpty()) {
            return str; // Handle null or empty strings
        }

        char firstChar = Character.toUpperCase(str.charAt(0));
        String restOfString = str.substring(1).toLowerCase();

        return firstChar + restOfString;
    }

    private long convertDateToEpoch(LocalDate date)
    {
        return date.atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC);
    }

    private void isCityExist (String cityName)
    {

        List<City> cities = cityRepository.findAll();

        for (City city : cities)
        {
            if (city.getLocalNames().containsValue(cityName))
            {
                return;
            }

        }
        logger.error("Invalid city given by the user: {}", cityName);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found.");
    }

    private void isDateValid (LocalDate startDate, LocalDate endDate)
    {
        logger.info("Dates given by the user: {} - {}", startDate, endDate);

        if (startDate.isBefore(LocalDate.of(2020, 10, 27)) || endDate.isBefore(LocalDate.of(2020, 10, 27)))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date. Only queries allowed after 2020-10-27");
        }
    }

    private List<WeatherQuality> checkDb(String city, LocalDate startDate, LocalDate endDate)
    {
        return weatherQualityRepository.getWeatherQualityInRange(city, startDate, endDate);
    }

    // Check if there are any missing dates within the given date range
    private List<LocalDate> checkMissingDates(List<WeatherQuality> weatherQualities, LocalDate startDate, LocalDate endDate)
    {
        List<LocalDate> missingDates = null;
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
        {
            boolean found = false;
            for (WeatherQuality weatherQuality : weatherQualities)
            {
                LocalDate finalDate = date;
                if (weatherQuality.getResults().stream().anyMatch(result -> result.getDate().equals(finalDate)))
                {
                    logger.info("Data found for the date: {}", date);
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                missingDates.add(date);
            }
        }
        return missingDates;
    }

    public WeatherQueryResponseDto getWeatherQuality(String city, LocalDate startDate, LocalDate endDate)
    {
        String formattedCity = capitalize(city);

        // Void control methods.
        isCityExist(formattedCity);
        isDateValid(startDate, endDate);

        List<WeatherQuality> weatherQualities = checkDb(formattedCity ,startDate, endDate);
        WeatherApiResultDto openWeatherResults = null;
        List<AirSubstanceDto> airSubstanceDtos = new ArrayList<>();
        List<ResultsDto> resultsDtos = new ArrayList<>();


        if (weatherQualities.isEmpty())
        {
            logger.warn("No data found in the database for the given date range: {} - {}", startDate, endDate);

            // Convert dates to epoch for OpenWeather API
            long startDateEpoch = startDate.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
            long endDateEpoch = endDate.atStartOfDay(ZoneOffset.UTC).toEpochSecond();

            // Retrieve lat and lon for the city
            City cityObj = cityRepository.findByName(formattedCity);
            String lat = cityObj.getLat();
            String lon = cityObj.getLon();


            // Call OpenWeather API
            try{
                logger.info("Calling OpenWeather API for the city: {}", formattedCity);
                openWeatherResults =  openWeatherClient.getWeatherData(String.format("http://api.openweathermap.org/data/2.5/air_pollution/history?lat=%s&lon=%s&start=%d&end=%d", lat, lon, startDateEpoch, endDateEpoch));
            }
            catch (Exception e)
            {
                logger.error("Error while calling OpenWeather API: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while calling OpenWeather API");
            }

            // Take the results from OpenWeather API
            // Find the middle day epoch 12:00:00
            // Fetch CO, SO2, O3
            // Call enum to categorize those values
            for (WeatherApiListFieldDto list : openWeatherResults.getList())
            {
                LocalDateTime date = LocalDateTime.ofEpochSecond(list.getDt(), 0, java.time.ZoneOffset.UTC);
                if (date.getHour() == 12)
                {
                    logger.info("Data found for the date: {}", date);
                    Map<String, Float> components = list.getComponents();
                    logger.info("Components: CO: {} O3: {} SO2: {}", components.get("co"), components.get("o3"), components.get("so2"));


                    // Categorize the values in the requirements list.
                    String coCategory = COPollutantCategories.getCategory(components.get("co")).toString();
                    String o3Category = O3PollutantCategories.getCategory(components.get("o3")).toString();
                    String so2Category = SO2PollutantCategories.getCategory(components.get("so2")).toString();
                    logger.info("Categories: CO: {} O3: {} SO2: {}", coCategory, o3Category, so2Category);


                    // Now add these into airsubstance list.
                    Map<String, String> categoryMap = new HashMap<>();
                    categoryMap.put("co", coCategory);
                    categoryMap.put("o3", o3Category);
                    categoryMap.put("so2", so2Category);

                    AirSubstanceDto airSubstanceDto = AirSubstanceDto.builder()
                            .category(categoryMap)
                            .build();

                    airSubstanceDtos.add(airSubstanceDto);

                    // Then add the results to the results list.
                    ResultsDto resultsDto = ResultsDto.builder()
                            .date(date.toLocalDate())
                            .categories(airSubstanceDtos)
                            .build();
                    resultsDtos.add(resultsDto);
                }
            }
            // Save the results to the database
            WeatherQuality weatherQuality = WeatherQuality.builder()
                    .city(formattedCity)
                    .results(resultsDtos)
                    .build();

            weatherQualityRepository.save(weatherQuality);
            logger.info("Data saved to the database for the city: {}", formattedCity);
        }
        return null;
    }
}
