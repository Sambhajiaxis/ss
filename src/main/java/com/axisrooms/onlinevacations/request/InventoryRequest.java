package com.axisrooms.onlinevacations.request;

import com.axisrooms.onlinevacations.bean.InventoryData;
import com.axisrooms.onlinevacations.request.validation.ValidInventoryRequest;
import com.axisrooms.onlinevacations.bean.InventoryData;
import com.axisrooms.onlinevacations.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@ValidInventoryRequest
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryRequest {
   @JsonProperty("channelId")
    private String              channelId;
    @JsonProperty("token")
    private String              token;
   @JsonProperty("arcRequestId")
    private String              arcRequestId;
    @JsonProperty("hotelId")
    private String              hotelId;
    @JsonProperty("data")
    private List<InventoryData> data;









}
