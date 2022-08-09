
package com.axisrooms.onlinevacations.generated.updateInventory;

import com.axisrooms.onlinevacations.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    @JsonProperty("key")
    private String key;
    /*@JsonProperty("inventory")
    private List<Inventory> inventory;*/
    @JsonProperty("property")
    private String property;
    @JsonProperty("room")
    private String room;
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @JsonProperty("start_date")
    private LocalDate    start_date;
    @JsonProperty("end_date")
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    private LocalDate end_date;
    @JsonProperty("inventory")
    private Integer      inventory;

}
