package com.axisrooms.onlinevacations.request;

import com.axisrooms.onlinevacations.bean.PriceData;
import com.axisrooms.onlinevacations.request.validation.ValidPriceRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@ValidPriceRequest
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceRequest {
    @JsonProperty("channelId")
    private String          channelId;
    @JsonProperty("token")
    private String          token;
    @JsonProperty("arcRequestId")
    private String          arcRequestId;
    @JsonProperty("hotelId")
    private String          hotelId;
    @JsonProperty("data")
    private List<PriceData> data;
}
