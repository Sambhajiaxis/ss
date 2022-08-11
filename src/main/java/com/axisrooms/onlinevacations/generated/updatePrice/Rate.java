
package com.axisrooms.onlinevacations.generated.updatePrice;

import com.axisrooms.onlinevacations.util.Constants;
import com.axisrooms.onlinevacations.util.OccupancyNotSupportedException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Rate {
    //add more if required
    private String    SINGLE;
    private String    DOUBLE;
    private String    TWIN;
    private String    TRIPLE;
    private String    QUAD;
    private String    PENTA;
    private String    HEXA;
    private String    HEPTA;
    private String    OCTA;
    private String    NINE;
    private String    TEN;
    private String    ELEVEN;
    private String    TWELVE;
    private String    THIRTEEN;
    private String    FOURTEEN;
    private String    FIFTEEN;
    private String    SIXTEEN;
    private String    TWENTY;
    private String    THIRTY;
    private String    EXTRAPERSON;
    private String    EXTRABED;
    private String    EXTRAADULT;
    private String    EXTRAADULT2;
    private String    EXTRAADULT3;
    private String    EXTRAINFANT;
    private String    EXTRACHILD;
    private String    EXTRA_CHILD_BELOW_FIVE;
    private String    EXTRA_CHILD_ABOVE_FIVE;
    private String    EXTRACHILD2;
    private String    EXTRACHILD3;
  //  @JsonFormat(pattern = Constants.DATE_PATTERN)
    private String endDate;
 //   @JsonFormat(pattern = Constants.DATE_PATTERN)
    private String startDate;

    public String getendDate() {
        return endDate;
    }

    public void setendDate(String endDate) {
        this.endDate = endDate;
    }

    public String getstartDate() {
        return startDate;
    }

    public void setstartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setPriceByOccupancyName(String occupancyName, String price) throws OccupancyNotSupportedException {
     switch (occupancyName.toLowerCase())
     {
        case "single": this.setSINGLE(price);
                        break;
        case "double": this.setDOUBLE(price);
                        break;
        case "twin":   this.setTWIN(price);
                        break;
        case "triple": this.setTRIPLE(price);
                        break;
        case "quad":   this.setQUAD(price);
                        break;
        case "penta":  this.setPENTA(price);
                        break;
        case "hexa":   this.setHEXA(price);
                        break;
        case "hepta":  this.setHEPTA(price);
                        break;
        case "octa":   this.setOCTA(price);
                        break;
        case "nine":   this.setNINE(price);
                        break;
        case "ten":    this.setTEN(price);
                        break;
        case "eleven":   this.setELEVEN(price);
                            break;
        case "twelve":   this.setTWELVE(price);
                            break;
        case "thirteen": this.setTHIRTEEN(price);
                            break;
        case "fourteen": this.setFOURTEEN(price);
                            break;
        case "fifteen":  this.setFIFTEEN(price);
                            break;
        case "sixteen":  this.setSIXTEEN(price);
                            break;
        case "twenty":   this.setTWENTY(price);
                            break;
        case "thirty":   this.setTHIRTY(price);
                            break;
        case "extraperson":  this.setEXTRAPERSON(price);
                                break;
        case "extrabed":     this.setEXTRABED(price);
                                break;
        case "extraadult":   this.setEXTRAADULT(price);
                                break;
        case "extraadult2":   this.setEXTRAADULT2(price);
             break;
        case "extraadult3":   this.setEXTRAADULT3(price);
             break;
        case "extrainfant":  this.setEXTRAINFANT(price);
                                break;
        case "extrachild":   this.setEXTRACHILD(price);
                                break;
        case "extrachildbelowfive":  this.setEXTRA_CHILD_BELOW_FIVE(price);
                                            break;
        case "extrachildabovefive":  this.setEXTRA_CHILD_ABOVE_FIVE(price);
                                            break;
        case "extrachild2": this.setEXTRACHILD2(price);
                                 break;
        case "extrachild3": this.setEXTRACHILD3(price);
             break;
        default:
            throw new OccupancyNotSupportedException(occupancyName+" occupancy is not supported");
     }
    }
}
