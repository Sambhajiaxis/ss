
package com.axisrooms.onlinevacations.generated.updateRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RatePlanDetails {
    @JsonProperty("ratePlanId")
    private String ratePlanId;
    @JsonProperty("restrictions")
    private Restrictions restrictions;

//    public String getRatePlanId() {
//        return ratePlanId;
//    }
//
//    public void setRatePlanId(String ratePlanId) {
//        this.ratePlanId = ratePlanId;
//    }
//
//    public List<Restrictions> getRestrictions() {
//        return restrictions;
//    }
//
//    public void setRestrictions(List<Restrictions> restrictions) {
//        this.restrictions = restrictions;
//    }
}
