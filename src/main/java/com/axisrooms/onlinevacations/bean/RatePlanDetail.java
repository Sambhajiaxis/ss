package com.axisrooms.onlinevacations.bean;

import com.axisrooms.onlinevacations.enums.Day;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatePlanDetail {
    @JsonProperty("ratePlanId")
    private String          ratePlanId;
    @JsonProperty("ratePlanName")
    private String          ratePlanName;
    //@JsonProperty("rateType")
    //private String          rateType;
    @JsonProperty("currency")
    private String          currency;
    //@JsonProperty("days")
    //private List<Day>       days;
    @JsonProperty("rates")
    private List<Rate>      rates;
    //@JsonProperty("closeOutDates")
    //private List<Period>    closeOutDates;
    //@JsonProperty("inventories")
    //private List<Inventory> inventories;
    @JsonProperty("restrictions")
    private Restriction     restrictions;
}
