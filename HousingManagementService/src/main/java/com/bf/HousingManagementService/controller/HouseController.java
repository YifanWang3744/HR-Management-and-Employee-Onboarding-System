package com.bf.HousingManagementService.controller;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.domain.response.*;
import com.bf.HousingManagementService.entity.*;
import com.bf.HousingManagementService.exception.AccessDeniedException;
import com.bf.HousingManagementService.exception.EmployeeNotFoundException;
import com.bf.HousingManagementService.service.RemoteEmployeeService;
import com.bf.HousingManagementService.service.RemoteHousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class HouseController {

    private RemoteHousingService remoteHouseService;
    private RemoteEmployeeService remoteEmployeeService;

    @Autowired
    public void setRemoteHouseService(RemoteHousingService remoteHouseService) {
        this.remoteHouseService = remoteHouseService;
    }

    @Autowired
    public void setRemoteEmployeeService(RemoteEmployeeService remoteEmployeeService) {
        this.remoteEmployeeService = remoteEmployeeService;
    }

    @GetMapping("/employee/house/{houseId}")
    HousingDetailsResponse getHouseById(@PathVariable Long houseId, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");

        HouseResponse houseResponse = remoteHouseService.getHouseById(houseId, jwt);

        if(!houseResponse.getResponseStatus().getSuccess())
            return HousingDetailsResponse.builder()
                .status(ResponseStatus.builder().success(false).message("house not found").build()).build();

        House house = houseResponse.getHouse();
        Employee employee = remoteEmployeeService.getProfileByUserId(jwt).getData();

        if (employee.getHouseId() != houseId)
            return HousingDetailsResponse.builder()
                    .status(ResponseStatus.builder().success(false).message("access denied").build()).build();

        List<Employee> employees = remoteEmployeeService.getAllProfiles(jwt).getData()
                .stream()
                .filter(e -> e.getHouseId() == houseId)
                .collect(Collectors.toList());
        String address = house.getAddress();
        List<Roommate> roommateList = new ArrayList<>();
        for (Employee e : employees) {
            roommateList.add(Roommate.builder()
                    .firstName(e.getFirstName())
                    .lastName(e.getLastName())
                    .preferredName(e.getPreferredName())
                    .phoneNumber(e.getCellPhone())
                    .build());
        }

        return HousingDetailsResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("Get housing details")
                                .build()
                )
                .address(address)
                .roommateList(roommateList)
                .build();
    }
}
