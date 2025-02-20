package com.issola.weather.common.categories;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum COPollutantCategories
{
    GOOD("Good", 0),
    SATISFACTORY("Satisfactory", 6),
    MODERATE("Moderate", 11),
    POOR("Poor", 36),
    SEVERE("Severe", 61),
    HAZARDOUS("Hazardous", 91);

    private final String category;
    private final float threshold;

    public static COPollutantCategories getCategoryFromValue(float value)
    {
        for (COPollutantCategories category : COPollutantCategories.values())
        {
            if (value <= category.threshold)
            {
                return category;
            }
        }
        return HAZARDOUS; // Default to HAZARDOUS if value exceeds all thresholds
    }
}