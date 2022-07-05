package com.axisrooms.onlinevacations.bean;

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
public class RoomDetail {
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("ratePlanDetails")
    private List<RatePlanDetail> ratePlanDetails;
    @JsonProperty("ratePlans")
    private List<RatePlan> ratePlans;
}
