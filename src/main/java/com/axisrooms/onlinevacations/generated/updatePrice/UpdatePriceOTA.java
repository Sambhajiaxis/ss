
package com.axisrooms.onlinevacations.generated.updatePrice;

import com.axisrooms.onlinevacations.util.OccupancyNotSupportedException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@lombok.Data
public class UpdatePriceOTA {
  /* @JsonProperty("auth")
    private Auth auth;
    @JsonProperty("data")
    private Data data;

    public Auth getauth() {
        return auth;
    }

    public void setauth(Auth auth) {
        this.auth = auth;
    }

    public Data getdata() {
        return data;
    }

    public void setdata(Data data) {
        this.data = data;
    }
*/

   /* "key": "api key",
            "start_date": "08/10/2021",
            "end_date": "10/10/2021",
            "property": 1,
            "room": 112,
            "mealplan": 2,
            "lesser_occupancy": 6300,
            "base_price": 6500,
            "extra_adults": 2000,
            "kids":500
}
Response:*/


    private String key;

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }


    private String end_date;
    //   @JsonFormat(pattern = Constants.DATE_PATTERN)
    private String start_date;

    @JsonProperty("property")
    private Integer property;
    @JsonProperty("mealplan")
    private Integer mealplan;
    @JsonProperty("room")
    private Integer room;

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

    public Integer getProperty() {
        return property;
    }

    public void setProperty(Integer property) {
        this.property = property;
    }

    public Integer getMealplan() {
        return mealplan;
    }

    public void setMealplan(Integer mealplan) {
        this.mealplan = mealplan;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    private String SINGLE;
    private String DOUBLE;
    private String TWIN;
    private String TRIPLE;
    private String QUAD;
    private String PENTA;
    private String HEXA;
    private String HEPTA;
    private String OCTA;
    private String NINE;
    private String TEN;
    private String ELEVEN;
    private String TWELVE;
    private String THIRTEEN;
    private String FOURTEEN;
    private String FIFTEEN;
    private String SIXTEEN;
    private String TWENTY;
    private String THIRTY;
    private String EXTRAPERSON;
    private String EXTRABED;
    private String EXTRAADULT;
    private String KIDS;
    private String EXTRAADULT2;
    private String EXTRAADULT3;
    private String EXTRAINFANT;
    private String EXTRACHILD;
    private String EXTRA_CHILD_BELOW_FIVE;
    private String EXTRA_CHILD_ABOVE_FIVE;
    private String EXTRACHILD2;
    private String EXTRACHILD3;


    private String base_occupancy;
    private String lesser_occupancy;
    private String extra_adults;
    private String kids;


    public void setPriceByOccupancyName(String occupancyName, String price) throws OccupancyNotSupportedException {
        switch (occupancyName.toLowerCase()) {
            case "single":
                this.setSINGLE(price);
                break;
            case "double":
                this.setDOUBLE(price);
                break;
            case "twin":
                this.setTWIN(price);
                break;
            case "triple":
                this.setTRIPLE(price);
                break;
            case "quad":
                this.setQUAD(price);
                break;
            case "penta":
                this.setPENTA(price);
                break;
            case "hexa":
                this.setHEXA(price);
                break;
            case "hepta":
                this.setHEPTA(price);
                break;
            case "octa":
                this.setOCTA(price);
                break;
            case "nine":
                this.setNINE(price);
                break;
            case "ten":
                this.setTEN(price);
                break;
            case "eleven":
                this.setELEVEN(price);
                break;
            case "twelve":
                this.setTWELVE(price);
                break;
            case "thirteen":
                this.setTHIRTEEN(price);
                break;
            case "fourteen":
                this.setFOURTEEN(price);
                break;
            case "fifteen":
                this.setFIFTEEN(price);
                break;
            case "sixteen":
                this.setSIXTEEN(price);
                break;
            case "twenty":
                this.setTWENTY(price);
                break;
            case "thirty":
                this.setTHIRTY(price);
                break;
            case "extraperson":
                this.setEXTRAPERSON(price);
                break;
            case "extrabed":
                this.setEXTRABED(price);
                break;
            case "extraadult":
                this.setEXTRAADULT(price);
                break;
            case "kids":
                this.setKIDS(price);
                break;
            case "extraadult2":
                this.setEXTRAADULT2(price);
                break;
            case "extraadult3":
                this.setEXTRAADULT3(price);
                break;
            case "extrainfant":
                this.setEXTRAINFANT(price);
                break;
            case "extrachild":
                this.setEXTRACHILD(price);
                break;
            case "extrachildbelowfive":
                this.setEXTRA_CHILD_BELOW_FIVE(price);
                break;
            case "extrachildabovefive":
                this.setEXTRA_CHILD_ABOVE_FIVE(price);
                break;
            case "extrachild2":
                this.setEXTRACHILD2(price);
                break;
            case "extrachild3":
                this.setEXTRACHILD3(price);
                break;

            default:
                throw new OccupancyNotSupportedException(occupancyName + " occupancy is not supported");
        }
    }
}