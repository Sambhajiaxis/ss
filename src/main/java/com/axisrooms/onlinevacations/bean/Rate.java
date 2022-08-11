package com.axisrooms.onlinevacations.bean;

import com.axisrooms.onlinevacations.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {
    @JsonProperty("startDate")
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    private LocalDate startDate;

    @JsonProperty("endDate")
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    private LocalDate endDate;
    @JsonProperty("tax")
    private Integer tax;
    @JsonProperty("commission")
    private Integer commission;
    @JsonProperty("prices")
    private Map<String,String> prices;
}
