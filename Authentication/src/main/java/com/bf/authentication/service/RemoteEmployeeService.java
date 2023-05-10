package com.bf.authentication.service;

import com.bf.authentication.domain.request.RemoteEmployeeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("employee-service")
public interface RemoteEmployeeService {

    @PostMapping(value = "/employee-service/hr/employee")
    void createEmployee(@RequestBody RemoteEmployeeRequest request, @RequestHeader("Authorization") String bearerToken);

    @GetMapping("/employee-service/hr/employee/{userId}")
    String getEmployeeIdByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String bearerToken);
}
