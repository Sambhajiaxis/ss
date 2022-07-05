package com.axisrooms.onlinevacations.manager;

import com.axisrooms.onlinevacations.request.InventoryRequest;
import com.axisrooms.onlinevacations.request.PriceRequest;
import com.axisrooms.onlinevacations.request.RestrictionRequest;
import com.axisrooms.onlinevacations.response.*;
import com.axisrooms.onlinevacations.util.OccupancyNotSupportedException;
import org.springframework.stereotype.Service;

@Service
public interface OTAManager {
    RoomResponse getRoomList(String hotelId) throws Exception;

    RatePlanResponse getRatePlans(String hotelId, String roomId) throws Exception;

    InventoryResponse updateInventory(InventoryRequest inventoryRequest) throws Exception;

    PriceResponse updatePrice(PriceRequest priceRequest) throws OccupancyNotSupportedException,Exception;

    RestrictionResponse updateRestriction(RestrictionRequest restrictionRequest) throws Exception;
}
