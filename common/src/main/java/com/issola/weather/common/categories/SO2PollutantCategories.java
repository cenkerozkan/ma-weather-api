package com.issola.weather.common.categories;

public enum SO2PollutantCategories
{
    GOOD("Good", 0),
    SATISFACTORY("Satisfactory", 150),
    MODERATE("Moderate", 500),
    POOR("Poor", 650),
    SEVERE("Severe", 800),
    HAZARDOUS("Hazardous", 900);

    private final String category;
    private final float threshold;

    SO2PollutantCategories(String category, float threshold)
    {
        this.category = category;
        this.threshold = threshold;
    }

    public static SO2PollutantCategories getCategory(float value)
    {
        for (SO2PollutantCategories category : SO2PollutantCategories.values())
        {
            if (value <= category.threshold)
            {
                return category;
            }
        }
        return SEVERE; // Default to SEVERE if value exceeds all thresholds
    }

    @Override
    public String toString()
    {
        return this.category;
    }
}