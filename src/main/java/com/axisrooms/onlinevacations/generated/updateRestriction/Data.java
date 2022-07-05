
package com.axisrooms.onlinevacations.generated.updateRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    @JsonProperty("propertyId")
    private String propertyId;
    @JsonProperty("roomDetails")
    private List<RoomDetails> roomDetails;

//    public String getpropertyId() {
//        return propertyId;
//    }
//
//    public void setpropertyId(String propertyId) {
//        this.propertyId = propertyId;
//    }
//
//    public List<RoomDetails> getroomdetails() {
//        return roomDetails;
//    }
//
//    public void setroomdetails(List<RoomDetails> roomDetails) {
//        this.roomDetails = roomDetails;
//    }
}
