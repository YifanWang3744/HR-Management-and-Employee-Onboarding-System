package com.bf.HousingManagementService.service;

import com.bf.HousingManagementService.domain.response.AllCarsResponse;
import com.bf.HousingManagementService.entity.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("application-service")
public interface RemoteApplicationService {

    @GetMapping("/application-service/car/{empId}")
    List<Car> findCarsByEmployeeId(@PathVariable String empId, @RequestHeader(name = "authorization") String jwt);
}
