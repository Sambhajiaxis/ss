
package com.axisrooms.onlinevacations.generated.updateRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RoomDetails {
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("ratePlanDetails")
    private List<RatePlanDetails> ratePlanDetails;

//    public String getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(String roomId) {
//        this.roomId = roomId;
//    }
//
//    public List<RatePlanDetails> getRatePlanDetails() {
//        return ratePlanDetails;
//    }
//
//    public void setRatePlanDetails(List<RatePlanDetails> ratePlanDetails) {
//        this.ratePlanDetails = ratePlanDetails;
//    }
}
