package com.axisrooms.onlinevacations.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Description {
    @JsonProperty("id")
    private String Id;
    @JsonProperty("name")
    private String name;

}
