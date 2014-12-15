package com.govdelivery.tms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {
    @JsonProperty("name")
    private String name;

    @JsonProperty("weather")
    private WeatherDescription[] weatherDescriptions;

    @JsonProperty("main")
    private WeatherStatistics weatherStatistics;

    public String getName() {
        return name;
    }

    public WeatherDescription[] getWeatherDescriptions() {
        return weatherDescriptions;
    }

    public WeatherStatistics getWeatherStatistics() {
        return weatherStatistics;
    }
}
