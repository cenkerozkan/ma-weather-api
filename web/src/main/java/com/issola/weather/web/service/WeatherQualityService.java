package com.issola.weather.web.service;

import com.issola.weather.common.categories.COPollutantCategories;
import com.issola.weather.common.categories.O3PollutantCategories;
import com.issola.weather.common.categories.SO2PollutantCategories;
import com.issola.weather.common.dto.ResultsDto;
import com.issola.weather.common.dto.WeatherApiListFieldDto;
import com.issola.weather.common.dto.WeatherApiResultDto;
import com.issola.weather.common.dto.WeatherQueryResponseDto;
import com.issola.weather.common.model.City;
import com.issola.weather.common.model.WeatherQuality;
import com.issola.weather.common.repository.ICityRepository;
import com.issola.weather.common.repository.IWeatherQualityRepository;
import com.issola.weather.web.client.IOpenWeatherClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class WeatherQualityService implements IWeatherQualityService
{
    private static final Logger logger = LoggerFactory.getLogger(WeatherQualityService.class);
    private final ICityRepository cityRepository;
    private final IWeatherQualityRepository weatherQualityRepository;
    private final IOpenWeatherClient openWeatherClient;

    private List<ResultsDto> fetchBatchOfData(WeatherQuality weatherQuality, boolean isNew, String city, LocalDate startDate, LocalDate endDate)
    {
        String formattedCity = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        List<ResultsDto> resultsDtos = weatherQuality.getResults();

        // Convert dates to epoch for OpenWeather API
        long startDateEpoch = startDate.atTime(12, 0, 0).toEpochSecond(ZoneOffset.UTC);
        long endDateEpoch = endDate.atTime(12, 0, 0).toEpochSecond(ZoneOffset.UTC);

        // Retrieve lat and lon for the city
        City cityObj = cityRepository.findByName(formattedCity);
        String lat = cityObj.getLat();
        String lon = cityObj.getLon();

        WeatherApiResultDto openWeatherResults = openWeatherClient.getWeatherData(lat, lon, startDateEpoch, endDateEpoch, formattedCity);

        // Take the results from OpenWeather API
        // Find the middle day epoch 12:00:00
        // Fetch CO, SO2, O3
        // Call enum to categorize those values
        for (WeatherApiListFieldDto list : openWeatherResults.getList())
        {
            LocalDateTime date = LocalDateTime.ofEpochSecond(list.getDt(), 0, ZoneOffset.UTC);
            if (date.getHour() == 12)
            {
                // Fetch CO, SO2, O3
                logger.info("Data found for the date: {}", date);
                Map<String, Float> components = list.getComponents();
                logger.info("Components: CO: {} O3: {} SO2: {}", components.get("co"), components.get("o3"), components.get("so2"));

                // Call enum to categorize those values
                String coCategory = COPollutantCategories.getCategoryFromValue(components.get("co")).getCategory();
                String o3Category = O3PollutantCategories.getCategoryFromValue(components.get("o3")).getCategory();
                String so2Category = SO2PollutantCategories.getCategoryFromValue(components.get("so2")).getCategory();
                logger.info("Categories: CO: {} O3: {} SO2: {}", coCategory, o3Category, so2Category);

                // Create category maps
                List<Map<String, String>> categories = new ArrayList<>();
                categories.add(Map.of("co", coCategory));
                categories.add(Map.of("o3", o3Category));
                categories.add(Map.of("so2", so2Category));

                // Create ResultsDto object
                ResultsDto results = ResultsDto.builder()
                        .date(date.toLocalDate())
                        .categories(categories)
                        .build();
                resultsDtos.add(results);
            }
        }
        if(isNew)
        {
            // Save the results before returning them.
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> weatherQualityRepository.save(weatherQuality));
        }
        else
        {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> weatherQualityRepository.updateResultsByCity(formattedCity, resultsDtos));
        }
        return resultsDtos;
    }

    @Override
    public WeatherQueryResponseDto getWeatherQuality(String city, LocalDate startDate, LocalDate endDate)
    {
        String formattedCity = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        logger.info("Getting weather quality for the city: {} between {} and {}", formattedCity, startDate, endDate);

        if(!cityRepository.isCityExistsByName(formattedCity))
        {
            logger.error("Invalid city given by the user: {}", formattedCity);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found.");
        }

        WeatherQuality weatherQuality = weatherQualityRepository.getWeatherQualityByCity(formattedCity);

        // No document found for the given city.
        if (weatherQuality == null)
        {
            logger.info("Data not found in the database. Fetching from OpenWeather API.");
            // Create new weather quality object
            WeatherQuality newWeatherQuality = WeatherQuality.builder()
                    .city(formattedCity)
                    .results(new ArrayList<>())
                    .build();
            return WeatherQueryResponseDto.builder()
                    .city(formattedCity)
                    .results(fetchBatchOfData(newWeatherQuality, true, formattedCity, startDate, endDate))
                    .build();
        }

        // Case: Data exists but there might be missing parts.
        else
        {
            logger.info("Data found in the database. Checking for missing parts.");

            // Create an array of dates to check
            List<ResultsDto> resultsDtos = weatherQuality.getResults();
            List<LocalDate> requestedDates = startDate.datesUntil(endDate.plusDays(1)).toList();
            List<LocalDate> missingDates = weatherQualityRepository.findMissingDates(formattedCity, startDate, endDate).getDates();
            WeatherQueryResponseDto responseDto = null;
            // Log dates for debug
            for (LocalDate date : requestedDates)
            {
                logger.info("Requested date: {}", date);
            }

            // If there are missing dates, fetch them
            // TODO: Change the algorithm of finding missing date ranges.
            if (!missingDates.isEmpty())
            {
                logger.info("Fetching missing data for {} dates", missingDates.size());
                for (int i = 0; i < missingDates.size();)
                {
                    LocalDate rangeStart = missingDates.get(i);
                    LocalDate rangeEnd = rangeStart;

                    // Find consecutive dates
                    while (i + 1 < missingDates.size() &&
                            missingDates.get(i + 1).equals(rangeEnd.plusDays(1)))
                    {
                        rangeEnd = missingDates.get(i + 1);
                        i++;
                    }

                    logger.info("Results are {}", fetchBatchOfData(weatherQuality, false, formattedCity, rangeStart, rangeEnd));

                    i++;
                }

                return WeatherQueryResponseDto.builder()
                        .city(formattedCity)
                        .results(weatherQualityRepository.getWeatherQualityByCity(formattedCity).getResults())
                        .build();
            }
            return WeatherQueryResponseDto.builder()
                    .city(formattedCity)
                    .results(resultsDtos)
                    .build();
        }
    }

    @Override
    public boolean deleteRecord(String city, LocalDate startDate, LocalDate endDate)
    {
        String formattedCity = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        if (!cityRepository.isCityExistsByName(formattedCity))
        {
            logger.error("Invalid city given by the user: {}", formattedCity);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found.");
        }
        logger.info("Deleting record for the city: {} between {} and {}", city, startDate, endDate);

        // First retrieve the document for the given city.
        WeatherQuality weatherQuality = weatherQualityRepository.getWeatherQualityByCity(city);
        List<ResultsDto> resultsList = weatherQuality.getResults();

        // Create a list of dates to delete
        List<LocalDate> datesToDelete = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1))
        {
            datesToDelete.add(date);
        }

        resultsList.removeIf(result -> datesToDelete.contains(result.getDate()));
        // Save the updated results
        weatherQualityRepository.updateResultsByCity(city, resultsList);
        return true;
    }
}
