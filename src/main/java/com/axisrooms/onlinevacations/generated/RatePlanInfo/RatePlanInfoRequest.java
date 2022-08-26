
package com.axisrooms.onlinevacations.generated.RatePlanInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RatePlanInfoRequest {

    @JsonProperty("auth")
    private Auth auth;
    @JsonProperty("key")
    private String key;
    @JsonProperty("property")
    private String property;
    @JsonProperty("room")
    private String room;
}
