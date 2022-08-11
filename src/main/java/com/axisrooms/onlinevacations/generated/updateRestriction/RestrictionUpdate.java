
package com.axisrooms.onlinevacations.generated.updateRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestrictionUpdate {

   /* @JsonProperty("auth")
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
*/

    @JsonProperty("key")
    private String key;
    @JsonProperty("property")
    private String property;
    @JsonProperty("room")
    private String room;
    @JsonProperty("mealplan")
    private String mealplan;
    @JsonProperty("start_date")
    // @JsonFormat(pattern = Constants.DATE_PATTERN)
    private String start_date;
    //    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @JsonProperty("end_date")
    private String end_date;
    @JsonProperty("stop_sell")
    private String stop_sell;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMealplan() {
        return mealplan;
    }

    public void setMealplan(String mealplan) {
        this.mealplan = mealplan;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStop_sell() {
        return stop_sell;
    }

    public void setStop_sell(String stop_sell) {
        this.stop_sell = stop_sell;
    }
}











