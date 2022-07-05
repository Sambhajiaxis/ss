package com.axisrooms.onlinevacations.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class RoomResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("httpStatusCode")
    private int httpStatusCode;
    @JsonProperty("descriptions")
    private Set<Description> descriptions;
}