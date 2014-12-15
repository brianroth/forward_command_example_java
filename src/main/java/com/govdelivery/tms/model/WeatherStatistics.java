package com.govdelivery.tms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherStatistics {
    @JsonProperty("temp")
    private double temp;

    public double getTemp() {
        return temp;
    }
}
