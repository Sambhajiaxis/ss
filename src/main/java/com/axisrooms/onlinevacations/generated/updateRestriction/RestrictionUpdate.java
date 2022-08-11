
package com.axisrooms.onlinevacations.generated.updateRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestrictionUpdate {
    @JsonProperty("auth")
    private Auth auth;
    @JsonProperty("data")
    private List<Data> data;

    public Auth getAuth() {
        return this.auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public List<Data> getData() {
        return this.data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
