
package com.axisrooms.onlinevacations.generated.updateInventory;

import com.axisrooms.onlinevacations.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryUpdate {



   /* @JsonProperty("auth")
   private Auth auth;
   @JsonProperty("data")
   private Data data;
*/





   @JsonProperty("key")
    private String key;
    @JsonProperty("property")
    private Integer property;
    @JsonProperty("room")
    private Integer room;
 //@JsonFormat(shape=JsonFormat.Shape.STRING
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @JsonProperty("start_date")
    private String    start_date;
    @JsonProperty("end_date")
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    private String end_date;
    @JsonProperty("inventory")
    private Integer      inventory;












}
