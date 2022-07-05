package com.axisrooms.onlinevacations.request;

import com.axisrooms.onlinevacations.bean.RestrictionData;
import com.axisrooms.onlinevacations.request.validation.ValidIRestrictionRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@ValidIRestrictionRequest
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestrictionRequest {
    @JsonProperty("channelId")
    private String                channelId;
    @JsonProperty("token")
    private String                token;
    @JsonProperty("arcRequestId")
    private String                arcRequestId;
    @JsonProperty("hotelId")
    private String                hotelId;
    @JsonProperty("data")
    private List<RestrictionData> data;
}
