package com.beaconfire.onboardingservice.service;

import com.beaconfire.onboardingservice.domain.HousingService.HouseAssignmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.ws.rs.Path;

@FeignClient("housing-management-service")
public interface RemoteHousingManagementService {

    @GetMapping("/housing-management-service/hr/house/new/{employeeId}")
    HouseAssignmentResponse assign(@PathVariable String employeeId, @RequestHeader("authorization") String jwt);
}
