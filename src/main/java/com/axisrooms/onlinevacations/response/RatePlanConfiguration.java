package com.axisrooms.onlinevacations.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RatePlanConfiguration {

    @JsonProperty("ratePlanId")
    private String ratePlanId;
    @JsonProperty("ratePlanName")
    private String ratePlanName;
    @JsonProperty("validityList")
    private List<RatePlanValidity> validityList;
    @JsonProperty("commission")
    private String commission;
    @JsonProperty("tax")
    private String tax;



}
