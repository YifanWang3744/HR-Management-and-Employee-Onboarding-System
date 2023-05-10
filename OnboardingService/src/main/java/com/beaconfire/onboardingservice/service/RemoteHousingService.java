package com.beaconfire.onboardingservice.service;
import com.beaconfire.onboardingservice.domain.HousingService.HouseAssignmentResponse;
import com.beaconfire.onboardingservice.domain.HousingService.HouseResponse;
import com.beaconfire.onboardingservice.exception.NoAvailableHouseException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("housing-service")
public interface RemoteHousingService {

    @GetMapping("/housing-service/{houseId}")
    HouseResponse getHouseById(@PathVariable Long houseId, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/housing-service/hr/house/new")
    HouseAssignmentResponse assign(@RequestHeader(name = "authorization") String jwt);
}
