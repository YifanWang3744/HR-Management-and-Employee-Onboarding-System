package com.bf.HousingManagementService.service;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.domain.response.EmployeeResponse;
import com.bf.HousingManagementService.domain.response.EmployeesResponse;
import com.bf.HousingManagementService.domain.response.OccupantResponse;
import com.bf.HousingManagementService.exception.EmployeeNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("employee-service")
public interface RemoteEmployeeService{
    @PostMapping("/employee-service/profile/house/new/{empId}")
    ResponseStatus updateEmployeeHouseInfo(@PathVariable String empId, @RequestParam Long houseId, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/employee-service/profile/employee")
    EmployeeResponse getProfileByUserId(@RequestHeader(name = "authorization") String jwt);

    @GetMapping("/employee-service/profile/employees")
    EmployeesResponse getAllProfiles(@RequestHeader(name = "authorization") String jwt);

    @PostMapping("/employee-service/profile/house")
    ResponseStatus removeEmployeesHouseInfo(@RequestParam Long houseId, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/employee-service/profile/occupants/{houseId}")
    OccupantResponse getOccupantsByHouseId(@PathVariable Long houseId, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/employee-service/profile/occupants/detail/{houseId}")
    EmployeesResponse getOccupantDetailsByHouseId(@PathVariable Long houseId, @RequestHeader(name = "authorization") String jwt);
}
