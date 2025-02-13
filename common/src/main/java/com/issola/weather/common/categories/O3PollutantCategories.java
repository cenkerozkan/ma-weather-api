package com.issola.weather.common.categories;

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

    O3PollutantCategories(String category, float threshold)
    {
        this.category = category;
        this.threshold = threshold;
    }

    public static O3PollutantCategories getCategory(float value)
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

    @Override
    public String toString()
    {
        return this.category;
    }
}