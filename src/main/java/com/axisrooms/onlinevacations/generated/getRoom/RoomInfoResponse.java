
package com.axisrooms.onlinevacations.generated.getRoom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RoomInfoResponse {
    @JsonProperty("message")
    private String Message;
    @JsonProperty("status")
    private String Status;
    @JsonProperty("datum")
    private List<Datum> Roomtypes;

}