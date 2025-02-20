package com.issola.weather.common.categories;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum O3PollutantCategories
{
    GOOD("Good", 0),
    SATISFACTORY("Satisfactory", 160),
    MODERATE("Moderate", 200),
    POOR("Poor", 300),
    SEVERE("Severe", 400),
    VERY_SEVERE("Very Severe", 800),
    HAZARDOUS("Hazardous", 1000);

    private final String category;
    private final float threshold;

    public static O3PollutantCategories getCategoryFromValue(float value)
    {
        for (O3PollutantCategories category : O3PollutantCategories.values())
        {
            if (value <= category.threshold)
            {
                return category;
            }
        }
        return HAZARDOUS;
    }
}