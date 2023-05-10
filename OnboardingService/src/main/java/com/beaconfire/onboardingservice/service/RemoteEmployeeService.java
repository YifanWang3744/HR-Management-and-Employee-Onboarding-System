package com.beaconfire.onboardingservice.service;
import com.beaconfire.onboardingservice.domain.EmployeeService.EmployeeResponse;
import com.beaconfire.onboardingservice.domain.EmployeeService.EmployeesResponse;
import com.beaconfire.onboardingservice.entity.EmployeeService.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient("employee-service")
public interface RemoteEmployeeService {

    @GetMapping("/employee-service/profile/employee")
    EmployeeResponse getProfileByUserId(@RequestHeader(name = "authorization") String jwt);

    @GetMapping("/employee-service/profile/employee/{employeeId}")
    EmployeeResponse getProfileByUserId(@PathVariable String employeeId, @RequestHeader(name = "authorization") String jwt);

    @PostMapping("/employee-service/profile/employee")
    ResponseStatus saveOrUpdateEmployee(@RequestBody Employee employee, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/employee-service/profile")
    EmployeesResponse getAllProfiles(@RequestHeader(name = "authorization") String jwt);

    @PostMapping("/employee-service/profile/house/{employee}")
    ResponseStatus removeEmployeesHouseInfo(@RequestParam Long houseId, @PathVariable String empId, @RequestHeader(name = "authorization") String jwt);

    @PostMapping("/employee-service/profile/house/new/{employee}")
    ResponseStatus updateEmployeeHouseInfo(@RequestParam Long houseId, @PathVariable String empId, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/employee-service/hr/test")
    void test();
}