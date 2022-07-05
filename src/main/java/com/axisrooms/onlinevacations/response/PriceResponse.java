package com.axisrooms.onlinevacations.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PriceResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("httpStatusCode")
    private int httpStatusCode;
}
