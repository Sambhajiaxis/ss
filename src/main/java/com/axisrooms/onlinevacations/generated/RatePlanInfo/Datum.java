
package com.axisrooms.onlinevacations.generated.RatePlanInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@lombok.Data
public class Datum {
    @JsonProperty("id")
    private String Id;

    @JsonProperty("name")
    private String Name;



    @JsonProperty("commissionPerc")
    private String CommissionPerc;
    @JsonProperty("occupancy")
    private List<String> Occupancy;
    @JsonProperty("ratePlanName")
    private String RatePlanName;
    @JsonProperty("rateplanId")
    private String RatePlanId;
    @JsonProperty("taxPerc")
    private String TaxPerc;
    @JsonProperty("validity")
    private Validity Validity;
    @JsonProperty("currency")
    private String Currency;


}
