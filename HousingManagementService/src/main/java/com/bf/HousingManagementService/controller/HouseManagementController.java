package com.bf.HousingManagementService.controller;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.domain.response.*;
import com.bf.HousingManagementService.domain.resultWrapper.EmployeeWrapper;
import com.bf.HousingManagementService.domain.resultWrapper.HouseDetail;
import com.bf.HousingManagementService.domain.resultWrapper.HouseSummary;
import com.bf.HousingManagementService.exception.EmployeeNotFoundException;
import com.bf.HousingManagementService.exception.NoAvailableHouseException;
import com.bf.HousingManagementService.service.RemoteApplicationService;
import com.bf.HousingManagementService.service.RemoteEmployeeService;
import com.bf.HousingManagementService.service.RemoteHousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * hr manages house
 */

@RestController
@RequestMapping("/hr")
public class HouseManagementController {
    private RemoteHousingService remoteHouseService;
    private RemoteEmployeeService remoteEmployeeService;
    private RemoteApplicationService remoteApplicationService;

    @Autowired
    public void setRemoteEmployeeService(RemoteEmployeeService remoteEmployeeService) {
        this.remoteEmployeeService = remoteEmployeeService;
    }

    @Autowired
    public void setRemoteApplicationService(RemoteApplicationService remoteApplicationService) {
        this.remoteApplicationService = remoteApplicationService;
    }

    @Autowired
    public void setRemoteHouseService(RemoteHousingService remoteHouseService) {
        this.remoteHouseService = remoteHouseService;
    }

    @GetMapping("/house/new/{empId}")
    @PreAuthorize("hasAuthority('HR')")
    public HouseAssignmentResponse assign(@PathVariable String empId, HttpServletRequest request)  {
        String jwt = request.getHeader("Authorization");

        Long houseId = remoteHouseService.assign(jwt).getHouseId();
            if(houseId == null) return HouseAssignmentResponse
                .builder()
                .status(ResponseStatus.builder().success(false).message("House not assigned").build())
                .build();

            remoteEmployeeService.updateEmployeeHouseInfo(empId, houseId, jwt);

        return HouseAssignmentResponse
                .builder()
                .status(ResponseStatus.builder().success(true).message("House successfully assigned").build())
                .houseId(houseId)
                .build();
    }

    @GetMapping("/houses")
    @PreAuthorize("hasAuthority('HR')")
    public HouseSummariesResponse viewHouses(HttpServletRequest request){
        String jwt = request.getHeader("Authorization");

        List<HouseSummary> houseSummaries = remoteHouseService.viewHouses(jwt).getData();

        houseSummaries.forEach(h->{
            OccupantResponse response = remoteEmployeeService.getOccupantsByHouseId(h.getHouseId(), jwt);
            if(response.getStatus().getSuccess()) h.setOccupantsNum(response.getNum());
        });

        return HouseSummariesResponse
                .builder()
                .status(ResponseStatus.builder().success(true).message("House summaries fetched").build())
                .data(houseSummaries)
                .build();
    }

    @GetMapping("/house/{houseId}")
    @PreAuthorize("hasAuthority('HR')")
    public HouseDetailResponse viewHouseById(@PathVariable Long houseId, @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size, HttpServletRequest request){
        String jwt = request.getHeader("Authorization");
        HouseDetail detail = remoteHouseService.viewHouseById(houseId, page, size, jwt).getData();
        if(detail == null) return HouseDetailResponse.builder().status(ResponseStatus.builder().success(false).message("house not found").build()).build();
        EmployeesResponse response = remoteEmployeeService.getOccupantDetailsByHouseId(houseId, jwt);
        if(!response.getStatus().getSuccess()) return HouseDetailResponse
                .builder()
                .status(ResponseStatus.builder().success(false).message("House detail not fetched").build())
                .build();

        List<EmployeeWrapper> occupants = response.getData().stream()
                .map(e -> EmployeeWrapper
                        .builder()
                        .id(e.getId())
                        .profilePicture(e.getProfilePicture())
                        .name((e.getPreferredName() == null || e.getPreferredName().isEmpty()) ? e.getFirstName() + " " + e.getLastName() : e.getPreferredName())
                        .phone(e.getCellPhone())
                        .car(remoteApplicationService.findCarsByEmployeeId(e.getId(), jwt).stream().findAny().orElse(null))
                        .build())
                .collect(Collectors.toList());

        detail.setOccupants(occupants);

        return HouseDetailResponse
                .builder()
                .status(ResponseStatus.builder().success(true).message("House detail fetched").build())
                .data(detail)
                .build();
    }

    @DeleteMapping("/house/{houseId}")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseStatus deleteHouse(@PathVariable Long houseId, HttpServletRequest request){
        String jwt = request.getHeader("Authorization");
        remoteHouseService.deleteHouse(houseId, jwt);
        remoteEmployeeService.removeEmployeesHouseInfo(houseId, jwt);
        return ResponseStatus.builder().success(true).message("house deleted").build();
    }
}
