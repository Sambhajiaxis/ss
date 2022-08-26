package com.axisrooms.onlinevacations.manager;

import com.axisrooms.onlinevacations.bean.*;
import com.axisrooms.onlinevacations.generated.RatePlanInfo.RatePlanInfoRequest;
import com.axisrooms.onlinevacations.generated.RatePlanInfo.RatePlanInfoResponse;
import com.axisrooms.onlinevacations.generated.RatePlanInfo.Validity;
import com.axisrooms.onlinevacations.generated.productInfo.Datum;
import com.axisrooms.onlinevacations.generated.productInfo.ProductInfoRequest;
import com.axisrooms.onlinevacations.generated.productInfo.ProductInfoResponse;
import com.axisrooms.onlinevacations.generated.updateInventory.Data;
import com.axisrooms.onlinevacations.generated.updateInventory.InventoryUpdate;
import com.axisrooms.onlinevacations.generated.updateInventory.InventoryUpdateResponse;
import com.axisrooms.onlinevacations.generated.updatePrice.PriceUpdateResponse;
import com.axisrooms.onlinevacations.generated.updatePrice.Rate;
import com.axisrooms.onlinevacations.generated.updatePrice.RateUpdateResponse;
import com.axisrooms.onlinevacations.generated.updatePrice.UpdatePriceOTA;
import com.axisrooms.onlinevacations.generated.updateRestriction.*;
import com.axisrooms.onlinevacations.model.TransactionLog;
import com.axisrooms.onlinevacations.repository.AxisroomsOtaRepository;
import com.axisrooms.onlinevacations.request.InventoryRequest;
import com.axisrooms.onlinevacations.request.PriceRequest;
import com.axisrooms.onlinevacations.request.RestrictionRequest;
import com.axisrooms.onlinevacations.response.*;
import com.axisrooms.onlinevacations.util.MarshalUnmarshalUtils;
import com.axisrooms.onlinevacations.util.OccupancyNotSupportedException;
import com.axisrooms.onlinevacations.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.axisrooms.onlinevacations.util.Constants.SUCCESS;

/**
 * actual call to the external api in ota side will happen from here
 */
@Service
@Slf4j
@Primary
public class OnlinevacationsOtaManager implements OTAManager {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${getRoomsUrl}")
    private String getRoomsUrl;

    @Value("${getUpdateInvUrl}")
    private String getUpdateInvUrl;

    @Value("${getUpdatePriceUrl}")
    private String getUpdatePriceUrl;

    @Value("${getProductInfoUrl}")
    private String getProductInfoUrl;

    @Value("${getUpdateRestrictionUrl}")
    private String getUpdateRestrictionUrl;

   /* @Value("${onlinevacations-ota.communication.userName}")
    private String username;

    @Value("${onlinevacations-ota.communication.password}")
    private String password;*/

    @Value("${onlinevacations-ota.communication.apitoken}")
    private String apiKey;

    @Autowired
    private AxisroomsOtaRepository repository;

    @Override
    public RoomResponse getRoomList(String hotelId) throws Exception {
        ProductInfoRequest productInfoRequest = buildProductInfoRequest(hotelId);
        ProductInfoResponse productInfoResponse = getProductInfo(productInfoRequest);
        RoomResponse roomResponse = buildRoomResponse(productInfoResponse);
        return roomResponse;
    }

    @Override
    public RatePlanResponse getRatePlans(String hotelId, String roomId) throws Exception {
        RatePlanInfoRequest ratePlanInfoRequest = buildRatePlanInfoRequest(hotelId, roomId);
//        ProductInfoRequest productInfoRequest = buildProductInfoRequest(hotelId);
        RatePlanInfoResponse ratePlanInfoResponse = getRatePlanInfo(ratePlanInfoRequest);
//        ProductInfoResponse productInfoResponse = getProductInfo(productInfoRequest);
        RatePlanResponse ratePlanResponse = buildRatePlansResponse(ratePlanInfoResponse, roomId);
        return ratePlanResponse;
    }

    @Override
    public InventoryResponse updateInventory(InventoryRequest inventoryRequest) throws Exception {
        TransactionLog transactionLog = new TransactionLog();
        Utils.addCommonData(inventoryRequest, transactionLog);
        InventoryResponse inventoryResponse = null;
        try {
            Utils.addCMRequest(inventoryRequest, transactionLog);
            List<InventoryUpdate> inventoryUpdateRequests = buildInventoryUpdateRequest(inventoryRequest);
            for (InventoryUpdate eachInventoryUpdate : inventoryUpdateRequests) {
                String jsonString = MarshalUnmarshalUtils.serialize(eachInventoryUpdate);
                log.info("update inventory request:: " + jsonString);
                Utils.addOTARequest(jsonString, transactionLog);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);
                ResponseEntity<String> responseEntity = restTemplate
                        .postForEntity(getUpdateInvUrl, entity, String.class);
                String responseJson = responseEntity.getBody();
                log.info("Response for update inventory....." + responseJson);
                if(responseJson.contains("S001"))
                {
                Utils.addOTAResponse(responseJson, transactionLog);
                InventoryUpdateResponse inventoryUpdateResponse = MarshalUnmarshalUtils.deserialize(responseJson, InventoryUpdateResponse.class);
                inventoryUpdateResponse.setmessage("Availability Updated");
                inventoryUpdateResponse.setstatus("success");
                inventoryResponse = buildInventoryResponse(inventoryUpdateResponse);
                Utils.addCMResponse(inventoryResponse, transactionLog);
            }}

        } catch (Throwable throwable) {
            Utils.addOTAResponse(throwable, transactionLog);
            throw throwable;
        } finally {
            repository.save(transactionLog);
        }
        return inventoryResponse;
    }

    @Override
    public PriceResponse updatePrice(PriceRequest priceRequest) throws OccupancyNotSupportedException, Exception {
        TransactionLog transactionLog = new TransactionLog();
        Utils.addCommonData(priceRequest, transactionLog);
        RatePlanResponse ratePlanResponse = null;
        PriceResponse priceResponse = null;
        try {
            Utils.addCMRequest(priceRequest, transactionLog);
            for (UpdatePriceOTA updatePriceOTA : buildUpdatePriceRequests(priceRequest)) {
                String jsonString = MarshalUnmarshalUtils.serializeWithPropertyNaming(updatePriceOTA).toLowerCase();
                log.info("update price request:: " + jsonString);
                Utils.addOTARequest(jsonString, transactionLog);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(getUpdatePriceUrl, entity, String.class);
                String responseJson = responseEntity.getBody();
                log.info("Response for update price....." + responseJson);
                Utils.addOTAResponse(responseJson, transactionLog);
                if(responseJson.contains("S001"))
                {
                PriceUpdateResponse priceUpdateResponse = MarshalUnmarshalUtils.deserialize(responseJson, PriceUpdateResponse.class);
                priceUpdateResponse.setmessage("Rate Updated");
                priceUpdateResponse.setstatus("success");
                priceResponse = buildPriceResponse(priceUpdateResponse);
                Utils.addCMResponse(priceResponse, transactionLog);
            }}
        } catch (Throwable throwable) {
            Utils.addOTAResponse(throwable, transactionLog);
            throw throwable;
        } finally {
            repository.save(transactionLog);
        }
        return priceResponse;
    }

    @Override
    public RestrictionResponse updateRestriction(RestrictionRequest restrictionRequest) throws Exception {
        TransactionLog transactionLog = new TransactionLog();
        Utils.addCommonData(restrictionRequest, transactionLog);
        RestrictionResponse restrictionResponse = null;
        try {
            Utils.addCMRequest(restrictionRequest, transactionLog);
            List<RestrictionUpdate> restrictionUpdateRequests = buildRestrictionUpdateRequest(restrictionRequest);
            for (RestrictionUpdate eachRestrictionUpdate : restrictionUpdateRequests) {
                String jsonString = MarshalUnmarshalUtils.serialize(eachRestrictionUpdate);
                log.info("update restriction request:: " + jsonString);
                Utils.addOTARequest(jsonString, transactionLog);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);
                ResponseEntity<String> responseEntity = restTemplate
                        .postForEntity(getUpdateRestrictionUrl, entity, String.class);
                String responseJson = responseEntity.getBody();
                log.info("Response for update restrcition....." + responseJson);
                Utils.addOTAResponse(responseJson, transactionLog);
                if(responseJson.contains("S001"))
                {

                    RestrictionUpdateResponse restrictionUpdateResponse = MarshalUnmarshalUtils.deserialize(responseJson, RestrictionUpdateResponse.class);
                    restrictionUpdateResponse.setMessage("StopSell Updated Successfully");
                    restrictionUpdateResponse.setStatus("success");
                    restrictionResponse = buildRestrictionResponse(restrictionUpdateResponse);
                    Utils.addCMResponse(restrictionResponse, transactionLog);
                }}

        } catch (Throwable throwable) {
            log.error(throwable.toString(), throwable);
            Utils.addOTAResponse(throwable, transactionLog);
            throw throwable;
        } finally {
            repository.save(transactionLog);
        }
        return restrictionResponse;
    }

    /*
    Because common format has support for single room per request as of now
     */
    private List<InventoryUpdate> buildInventoryUpdateRequest(InventoryRequest inventoryRequest) {
        List<InventoryUpdate> inventoryUpdates = new ArrayList<InventoryUpdate>();
        for (InventoryData eachInventoryData : inventoryRequest.getData()) {
            InventoryUpdate inventoryUpdate = new InventoryUpdate();
            inventoryUpdates.add(inventoryUpdate);
            inventoryUpdate.setKey(apiKey);
            inventoryUpdate.setProperty(Integer.valueOf(inventoryRequest.getHotelId()));
            inventoryUpdate.setRoom(Integer.valueOf(eachInventoryData.getRoomId()));
            List<com.axisrooms.onlinevacations.generated.updateInventory.Inventory> inventories = new ArrayList<>();

            for (Inventory eachInventory : eachInventoryData.getInventories()) {
                com.axisrooms.onlinevacations.generated.updateInventory.Inventory inventory = new com.axisrooms.onlinevacations.generated.updateInventory.Inventory();

               /* SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                inventoryUpdate.setStart_date( sdf.format(eachInventory.getStartDate()));*/

                inventoryUpdate.setInventory(eachInventory.getInventory());
                inventoryUpdate.setStart_date(eachInventory.getStartDate());
                inventoryUpdate.setEnd_date(eachInventory.getEndDate());
                //   inventoryUpdate.setEnd_date(sdf.format(eachInventory.getEndDate()));


            }
        }
        return inventoryUpdates;
    }
    private InventoryResponse buildInventoryResponse(InventoryUpdateResponse inventoryUpdateResponse) {
        InventoryResponse inventoryResponse = new InventoryResponse();

        if (inventoryUpdateResponse.getstatus().equalsIgnoreCase("success")) {
            inventoryResponse.setMessage("Availability Updated");
            inventoryResponse.setHttpStatusCode(HttpStatus.OK.value());
        } else {
            inventoryResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            inventoryResponse.setMessage(inventoryUpdateResponse.getmessage());
        }
        return inventoryResponse;
    }

    private PriceResponse buildPriceResponse(PriceUpdateResponse priceUpdateResponse) {
        PriceResponse priceResponse = new PriceResponse();
        if (priceUpdateResponse.getstatus().equalsIgnoreCase("success")) {
            //priceResponse.setMessage("success");
            priceResponse.setMessage("Rate Updated");
            priceResponse.setHttpStatusCode(HttpStatus.OK.value());
        } else {
            priceResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            priceResponse.setMessage(priceUpdateResponse.getmessage());
        }
        return priceResponse;
    }

    private List<UpdatePriceOTA> buildUpdatePriceRequests(PriceRequest priceRequest)
            throws OccupancyNotSupportedException {
        List<UpdatePriceOTA> updatePriceOTAList = new ArrayList<>();
        UpdatePriceOTA updatePriceOTA = new UpdatePriceOTA();
        //  String hotelId = priceRequest.getHotelId();
        for (PriceData eachPriceData : priceRequest.getData()) {
            for (RoomDetail roomDetail : eachPriceData.getRoomDetails()) {
                updatePriceOTA.setRoom(Integer.valueOf(roomDetail.getRoomId()));
                for (RatePlanDetail ratePlanDetail : roomDetail.getRatePlanDetails()) {
                    try {
                        for (com.axisrooms.onlinevacations.bean.Rate eachRate : ratePlanDetail.getRates()) {
                            //multiple apis here
                            //      UpdatePriceOTA updatePriceOTA = new UpdatePriceOTA();
                            updatePriceOTAList.add(updatePriceOTA);

                            com.axisrooms.onlinevacations.generated.updatePrice.Auth auth = new com.axisrooms.onlinevacations.generated.updatePrice.Auth();
                            //  updatePriceOTA.setauth(auth);
                            auth.setkey(apiKey);

                            //my code
                            updatePriceOTA.setkey(apiKey);



                            //  com.axisrooms.onlinevacations.generated.updatePrice.Data data = new com.axisrooms.onlinevacations.generated.updatePrice.Data();
                            //       updatePriceOTA.setdata(data);
                            //     data.setpropertyId(hotelId);

                            //my code
                            //  inventoryUpdate.setProperty(Integer.valueOf(inventoryRequest.getHotelId()));
                            updatePriceOTA.setProperty(Integer.valueOf(priceRequest.getHotelId()));
                            //    updatePriceOTA.setRoom(roomDetail.getRoomId());
                            updatePriceOTA.setMealplan(Integer.valueOf(ratePlanDetail.getRatePlanId()));


                            List<Rate> rates = new ArrayList<>();
                            //   data.setrate(rates);
                            Map<String, String> priceMap = eachRate.getPrices();
                            //   Rate rate = new Rate();
                            // rates.add(rate);


                            //my code



                            updatePriceOTA.setStart_date(eachRate.getStartDate().toLowerCase());

                            updatePriceOTA.setEnd_date(eachRate.getEndDate().toLowerCase());




                            // rate.setendDate(eachRate.getEndDate());
                            //rate.setstartDate(eachRate.getStartDate());
                            // updatePriceOTA.setExtra_adults("50000");
                            //updatePriceOTA.setKids("2000");

                            for (String occupancy : priceMap.keySet()) {
                                updatePriceOTA.setPriceByOccupancyName(occupancy, priceMap.get(occupancy));
                            }
                        }
                    } catch (OccupancyNotSupportedException e) {
                        log.error("Exception while preparing price update api input:: " + e.getMessage());
                        throw e;
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw e;
                    }
                }
            }

        }
        return updatePriceOTAList;
    }


    private RatePlanResponse buildRatePlanResponse(RateUpdateResponse rateUpdateResponse) {
        RatePlanResponse ratePlanResponse = new RatePlanResponse();
        if (rateUpdateResponse.getStatus().equalsIgnoreCase("success")) {
            ratePlanResponse.setMessage("success");
            ratePlanResponse.setHttpStatusCode(HttpStatus.OK.value());
        } else {
            ratePlanResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            ratePlanResponse.setMessage(ratePlanResponse.getMessage());
        }
        return ratePlanResponse;
    }

    private ProductInfoRequest buildProductInfoRequest(String hotelId) {
        ProductInfoRequest productInfoRequest = new ProductInfoRequest();
        com.axisrooms.onlinevacations.generated.productInfo.Auth auth = new com.axisrooms.onlinevacations.generated.productInfo.Auth();
        productInfoRequest.setAuth(auth);
        auth.setKey(apiKey);
        productInfoRequest.setKey(apiKey);
        productInfoRequest.setProperty(hotelId);
        return productInfoRequest;
    }

    private ProductInfoResponse getProductInfo(ProductInfoRequest productInfoRequest) throws Exception {
        String jsonRequest = MarshalUnmarshalUtils.serialize(productInfoRequest);
        log.info("Input request to fetch rooms: -> " + jsonRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, httpHeaders);
        String response = response = restTemplate.postForObject(getRoomsUrl, entity, String.class);
//        String dummyData=readUsingBufferedReader("/home/tech/Desktop/otatestfile/getProductInfo.txt");
        log.info("Axisrooms ota getRooms Response=" + response);
        try {
            return MarshalUnmarshalUtils.deserialize(response, ProductInfoResponse.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            return MarshalUnmarshalUtils.deserialize(response, ProductInfoResponse.class);
        }
    }

    /*
    axisagent-commonOta conversion
     */
    private RoomResponse buildRoomResponse(ProductInfoResponse productInfoResponse) throws Exception {
        RoomResponse roomResponse = new RoomResponse();
        if (!productInfoResponse.getRoomtypes().isEmpty()) {
            productInfoResponse.setStatus("success");
            productInfoResponse.setMessage("succesfully fetch ");
        } else {
            productInfoResponse.setStatus("failure");
            productInfoResponse.setMessage("unable to  fetch ");
        }


        if (Objects.nonNull(productInfoResponse)) {
            log.info("productInfoResponse - " + productInfoResponse);
            log.info("productInfoResponse - " + productInfoResponse.getMessage());
            log.info("productInfoResponse - " + productInfoResponse.getStatus());

            if (productInfoResponse.getStatus().contains("success") || productInfoResponse.getStatus().contains("Success")) {

                Set<Description> descriptions = new HashSet<>();
                for (Datum datum : productInfoResponse.getRoomtypes()) {
                    Description description = new Description();
                    descriptions.add(description);
                    description.setId(datum.getId());
                    System.out.print(datum.getId());
                    description.setName(datum.getName());

                 /* description.setDisplay_name(datum.getDisplay_name());
                    description.setRoom_type(datum.getRoom_type());
                    description.setStatus(datum.getStatus());
*/


                }
                roomResponse.setDescriptions(descriptions);
                roomResponse.setMessage(SUCCESS);
                roomResponse.setHttpStatusCode(HttpStatus.OK.value());
            } else {
                roomResponse
                        .setMessage("request to fetch rooms failed from ota::  " + productInfoResponse.getMessage());
                roomResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } else {
            roomResponse.setMessage("Marshaling/Serialization Exception Occured.");
            roomResponse.setHttpStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        }
        return roomResponse;
    }

    private RatePlanInfoRequest buildRatePlanInfoRequest(String hotelId, String roomId) {
        RatePlanInfoRequest ratePlanInfoRequest = new RatePlanInfoRequest();
        com.axisrooms.onlinevacations.generated.RatePlanInfo.Auth auth = new com.axisrooms.onlinevacations.generated.RatePlanInfo.Auth();
        ratePlanInfoRequest.setAuth(auth);
        auth.setKey(apiKey);
        ratePlanInfoRequest.setKey(apiKey);
        ratePlanInfoRequest.setProperty(hotelId);
        ratePlanInfoRequest.setRoom(roomId);
        return ratePlanInfoRequest;
    }










    private RatePlanInfoResponse getRatePlanInfo(RatePlanInfoRequest ratePlanInfoRequest) throws Exception {
//        String jsonRequest = MarshalUnmarshalUtils.serialize(ratePlanInfoRequest);
        String jsonRequest = new ObjectMapper().writeValueAsString(ratePlanInfoRequest);
        log.info("Input request to fetch rate plans: -> " + jsonRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, httpHeaders);
        String response = "";
//        response = restTemplate.postForObject(getRoomsUrl, entity, String.class);  use this URL for getRooms
        response = restTemplate.postForObject(getProductInfoUrl, entity, String.class); //use this for getRatePlans
        log.info("Axisrooms ota get rate plans Response=" + response);
        return MarshalUnmarshalUtils.deserialize(response, RatePlanInfoResponse.class);
    }

    private RatePlanResponse buildRatePlansResponse(RatePlanInfoResponse ratePlanInfoResponse, String roomId) {
        RatePlanResponse ratePlanResponse = new RatePlanResponse();

        if (!ratePlanInfoResponse.getData().isEmpty())
        {
            ratePlanInfoResponse.setStatus("success");
            ratePlanInfoResponse.setMessage("succesfully fetch ");
        }
        else
        {
            ratePlanInfoResponse.setStatus("failure");
            ratePlanInfoResponse.setMessage("unable to  fetch ");
        }


        if (Objects.nonNull(ratePlanInfoResponse)) {
            if (ratePlanInfoResponse.getStatus().equalsIgnoreCase("success")) {
                ratePlanResponse.setHttpStatusCode(HttpStatus.OK.value());
                ratePlanResponse.setMessage("success");

                List<RatePlanDescription> ratePlanDescriptions = new ArrayList<>();
                ratePlanResponse.setRatePlanDescriptions(ratePlanDescriptions);
                RatePlanDescription ratePlanDescription = new RatePlanDescription();
                ratePlanDescriptions.add(ratePlanDescription);
                ratePlanDescription.setRoomId(roomId);
                List<RatePlanConfiguration> ratePlanConfigurations = new ArrayList<>();

                ratePlanDescription.setConfiguration(ratePlanConfigurations);
                for (com.axisrooms.onlinevacations.generated.RatePlanInfo.Datum datum : ratePlanInfoResponse.getData()) {
                    ratePlanResponse.setProperty(ratePlanInfoResponse.getProperty());
                    ratePlanResponse.setRoom(ratePlanInfoResponse.getRoom());
                    RatePlanConfiguration ratePlanConfiguration = new RatePlanConfiguration();




                    List<String> Occupancy=new ArrayList<>();
                    Occupancy.add("single");
                    Occupancy.add("Double ");
                    Occupancy.add("EXTRAADULT");
                    Occupancy.add("EXTRACHILD");
                    datum.setOccupancy(Occupancy);





                    ratePlanConfigurations.add(ratePlanConfiguration);
                    /*ratePlanConfiguration.setRatePlanId(datum.getId());
                    ratePlanConfiguration.setRatePlanName(datum.getName());*/

                    if (datum.getCommissionPerc() != null) {
                        ratePlanConfiguration.setCommission(datum.getCommissionPerc());
                    }
                    /*if (datum.getRatePlanId() != null) {
                        ratePlanConfiguration.setRatePlanId(datum.getRatePlanId());
                    }*/
                    if(datum.getId()!=null)
                    {
                        ratePlanConfiguration.setRatePlanId(datum.getId());
                    }
                    // ratePlanConfiguration.setRatePlanId("653");
                    log.info("rateplanId----------" + ratePlanConfiguration.getRatePlanId());
                    if (datum.getName()!= null && datum.getName() != "") {
                        ratePlanConfiguration.setRatePlanName(datum.getName());
                        log.info("ratePlanName if non null or non empty-----" + ratePlanConfiguration.getRatePlanName());
                    } else {
                        ratePlanConfiguration.setRatePlanName(datum.getRatePlanId());
                        log.info("rateplanName if else------------" + ratePlanConfiguration.getRatePlanName());
                    }
                    if (datum.getTaxPerc() != null) {
                        ratePlanConfiguration.setTax(datum.getTaxPerc());
                        log.info("tax------" + ratePlanConfiguration.getTax());
                    }
                    if (datum.getValidity() != null) {
                        Validity validity = datum.getValidity();
                        RatePlanValidity ratePlanValidity = new RatePlanValidity();
                        ratePlanValidity.setStartDate(validity.getStartDate());
                        ratePlanValidity.setEndDate(validity.getEndDate());
                        ratePlanConfiguration.setValidityList(new ArrayList<RatePlanValidity>(Arrays.asList(ratePlanValidity)));
                    }
                    ratePlanDescription.setCurrency(datum.getCurrency());
                    List<String> occupancies = new ArrayList<>();
                    ratePlanDescription.setOccupancies(occupancies);

                    for (String occupancy : datum.getOccupancy()) {
                        occupancies.add(occupancy);
                    }
                }
            } else {
                ratePlanResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                ratePlanResponse.setMessage("External api call failed " + ratePlanInfoResponse.getMessage());
            }
        } else {
            ratePlanResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            ratePlanResponse.setMessage("Marshalling error occurred");
        }
        return ratePlanResponse;
    }
    //new code

    //new code

    private List<RestrictionUpdate> buildRestrictionUpdateRequest(RestrictionRequest restrictionRequest) throws JsonProcessingException {
        log.info("In buildRestrictionUpdateRequest method---------");
        List<RestrictionUpdate> restrictionUpdates = new ArrayList<RestrictionUpdate>();
        for (RestrictionData eachRestrictionData : restrictionRequest.getData()) {
            RestrictionUpdate restrictionUpdate = new RestrictionUpdate();
            restrictionUpdates.add(restrictionUpdate);
            com.axisrooms.onlinevacations.generated.updateRestriction.Auth auth = new com.axisrooms.onlinevacations.generated.updateRestriction.Auth();
            restrictionUpdate.setKey(apiKey);
            List<com.axisrooms.onlinevacations.generated.updateRestriction.Data> data = new ArrayList<>();
            // restrictionUpdate.setData(data);
            for(com.axisrooms.onlinevacations.bean.RestrictionData eachData:restrictionRequest.getData()){
                com.axisrooms.onlinevacations.generated.updateRestriction.Data datas = new com.axisrooms.onlinevacations.generated.updateRestriction.Data();
                restrictionUpdate.setProperty(restrictionRequest.getHotelId());

                List<com.axisrooms.onlinevacations.generated.updateRestriction.RoomDetails> roomDetails = new ArrayList<>();
                // datas.setRoomDetails(roomDetails);
                for (RoomDetail eachRoomDetail : eachRestrictionData.getRoomDetails()) {
                    com.axisrooms.onlinevacations.generated.updateRestriction.RoomDetails roomDetail = new com.axisrooms.onlinevacations.generated.updateRestriction.RoomDetails();
                    //roomDetails.add(roomDetail);
                    // roomDetail.setRoomId(eachRoomDetail.getRoomId());

                    restrictionUpdate.setRoom(eachRoomDetail.getRoomId());

                    List<com.axisrooms.onlinevacations.generated.updateRestriction.RatePlanDetails> ratePlanDetails = new ArrayList<>();
                    //  roomDetail.setRatePlanDetails(ratePlanDetails);
                    for (RatePlanDetail eachRateplanDetail : eachRoomDetail.getRatePlanDetails()) {
                        com.axisrooms.onlinevacations.generated.updateRestriction.RatePlanDetails rateplanDetail = new com.axisrooms.onlinevacations.generated.updateRestriction.RatePlanDetails();
                         ratePlanDetails.add(rateplanDetail);
                         restrictionUpdate.setMealplan(eachRateplanDetail.getRatePlanId());

                        com.axisrooms.onlinevacations.generated.updateRestriction.Restrictions restrictionsDetails = new Restrictions();
                         rateplanDetail.setRestrictions(restrictionsDetails);
                        if(eachRateplanDetail.getRestrictions().getType().equalsIgnoreCase("COA") || eachRateplanDetail.getRestrictions().getType().equalsIgnoreCase("COD")){
                            if(eachRateplanDetail.getRestrictions().getValue().equalsIgnoreCase("true")){
                                restrictionsDetails.setValue("Close");
                            } else {
                                restrictionsDetails.setValue("Open");
                            }
                        } else {
                            restrictionsDetails.setValue(eachRateplanDetail.getRestrictions().getValue());
                        }
                        restrictionsDetails.setType(eachRateplanDetail.getRestrictions().getType());
                        List<com.axisrooms.onlinevacations.generated.updateRestriction.Periods> periods = new ArrayList<>();
                        //   restrictionsDetails.setPeriods(periods);
                        for (Period eachPeriod : eachRateplanDetail.getRestrictions().getPeriods()) {
                            com.axisrooms.onlinevacations.generated.updateRestriction.Periods periodsDetail = new com.axisrooms.onlinevacations.generated.updateRestriction.Periods();
                            // periods.add(periodsDetail);
                            //periodsDetail.setStartDate(eachPeriod.getStartDate());
                            //periodsDetail.setEndDate(eachPeriod.getEndDate());
                            // restrictionUpdate.setStop_sell("1");
                            restrictionUpdate.setStop_sell(restrictionsDetails.getValue());


                            restrictionUpdate.setStart_date(eachPeriod.getStartDate());
                            restrictionUpdate.setEnd_date(eachPeriod.getEndDate());

                        }

                        //adding another rateplan for maxlos
                        if(eachRateplanDetail.getRestrictions().getType().equalsIgnoreCase("MLOS")
                                && (Objects.nonNull(eachRateplanDetail.getRestrictions().getMaxLOSValue()) && !eachRateplanDetail.getRestrictions().getMaxLOSValue().equalsIgnoreCase("0"))){
                            com.axisrooms.onlinevacations.generated.updateRestriction.RatePlanDetails ratePlanDetailsMAXLOS = new com.axisrooms.onlinevacations.generated.updateRestriction.RatePlanDetails();
                            ratePlanDetails.add(ratePlanDetailsMAXLOS);
                            ratePlanDetailsMAXLOS.setRatePlanId(rateplanDetail.getRatePlanId());
                            com.axisrooms.onlinevacations.generated.updateRestriction.Restrictions restrictionsDetailsMAXLOS = new Restrictions();
                            ratePlanDetailsMAXLOS.setRestrictions(restrictionsDetailsMAXLOS);
                            restrictionsDetailsMAXLOS.setPeriods(rateplanDetail.getRestrictions().getPeriods());
                            restrictionsDetailsMAXLOS.setType("MaxLos");
                            restrictionsDetailsMAXLOS.setValue(eachRateplanDetail.getRestrictions().getMaxLOSValue());
                        }

                    }
                }
            }
        }
        return restrictionUpdates;
    }


    private RestrictionResponse buildRestrictionResponse(RestrictionUpdateResponse restrictionUpdateResponse) {
        RestrictionResponse restrictionResponse = new RestrictionResponse();
        if (restrictionUpdateResponse.getStatus().equalsIgnoreCase("success")) {
           // restrictionResponse.setMessage("success");
            restrictionResponse.setMessage("StopSell Updated Successfully");
            restrictionResponse.setHttpStatusCode(HttpStatus.OK.value());
        } else {
            restrictionResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restrictionResponse.setMessage(restrictionUpdateResponse.getMessage());
        }
        return restrictionResponse;
    }
}