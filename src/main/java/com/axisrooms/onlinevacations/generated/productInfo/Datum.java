
package com.axisrooms.onlinevacations.generated.productInfo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Datum {
    @JsonProperty("id")
    private String Id;
    @JsonProperty("name")
    private String Name;
}
