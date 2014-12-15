package com.govdelivery.tms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDescription {
    @JsonProperty("main")
    private String type;

    @JsonProperty("description")
    private String details;

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }
}
