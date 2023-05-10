package com.bf.HousingManagementService.service;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.domain.response.*;
import com.bf.HousingManagementService.exception.NoAvailableHouseException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("housing-service")
public interface RemoteHousingService {
    @DeleteMapping("/housing-service/hr/house/{houseId}")
    ResponseStatus deleteHouse(@PathVariable Long houseId, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/housing-service/hr/house/{houseId}")
    HouseDetailResponse viewHouseById(@PathVariable Long houseId, @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "3") int size, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/housing-service/hr/houses")
    HouseSummariesResponse viewHouses(@RequestHeader(name = "authorization") String jwt);

    @GetMapping("/housing-service/hr/house/new")
    HouseAssignmentResponse assign(@RequestHeader(name = "authorization") String jwt);

    @GetMapping("/housing-service/house/{houseId}")
    HouseResponse getHouseById(@PathVariable Long houseId, @RequestHeader(name = "authorization") String jwt);
}
