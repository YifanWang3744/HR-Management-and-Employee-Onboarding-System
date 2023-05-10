package com.team3.employeeservice.controller;

import com.team3.employeeservice.domain.Employee;
import com.team3.employeeservice.request.RemoteEmployeeRequest;
import com.team3.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr")
public class EmployeeAuthController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    @PreAuthorize("hasAuthority('HR')")
    public void createEmployee(@RequestBody RemoteEmployeeRequest request){
        Employee employee = Employee.builder().userId(request.getUserId()).email(request.getEmail()).build();
        employeeService.saveOrUpdateEmployee(employee);
    }

    @GetMapping("/employee/{userId}")
    @PreAuthorize("permitAll()")
    public String getEmployeeIdByUserId(@PathVariable Long userId){
        return employeeService.findEmployeeIdByUserId(userId);
    }

//    @GetMapping("/test")
//    public String test(){
//        String employeeId =(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return employeeId;
//    }
}
