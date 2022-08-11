package com.axisrooms.onlinevacations.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RatePlanResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("httpStatusCode")
    private int httpStatusCode;
    @JsonProperty("property")
    private String property;
    @JsonProperty("room")
    private String room;

    @JsonProperty("ratePlanDescriptions")
    private List<RatePlanDescription> ratePlanDescriptions;
}
