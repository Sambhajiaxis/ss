
package com.axisrooms.onlinevacations.generated.getRoom;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Datum {
    @JsonProperty("room_id")
    private String Id;
    @JsonProperty("name")
    private String Name;
   /* @JsonProperty("display_name")
    private String Display_name;
    @JsonProperty("display_name")
    private String Room_type;
    @JsonProperty("status")
    private String status;
*/

}
