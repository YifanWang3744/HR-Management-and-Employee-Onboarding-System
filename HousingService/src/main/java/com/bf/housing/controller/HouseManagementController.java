package com.bf.housing.controller;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.domain.request.NewHouseRequest;
import com.bf.housing.domain.response.*;
import com.bf.housing.domain.resultWrapper.HouseDetail;
import com.bf.housing.domain.resultWrapper.HouseSummary;
import com.bf.housing.exception.NoAvailableHouseException;
import com.bf.housing.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * hr manages house
 */

@RestController
@RequestMapping("/hr")
public class HouseManagementController {
    private HouseService houseService;

    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/house/new")
    @PreAuthorize("hasAuthority('HR')")
    public HouseAssignmentResponse assign() {
        Long houseId;
        try {
            houseId = houseService.assign();
        }catch (NoAvailableHouseException e){

            return HouseAssignmentResponse
                    .builder()
                    .status(ResponseStatus.builder().success(false).message("House not available").build())
                    .build();
        }
        return HouseAssignmentResponse
                .builder()
                .status(ResponseStatus.builder().success(true).message("House successfully assigned").build())
                .houseId(houseId)
                .build();
    }

    @GetMapping("/houses")
    @PreAuthorize("hasAuthority('HR')")
    public HouseSummariesResponse viewHouses(){
        List<HouseSummary> houseSummaries = houseService.viewHouses();

        return HouseSummariesResponse
                .builder()
                .status(ResponseStatus.builder().success(true).message("House summaries fetched").build())
                .data(houseSummaries)
                .build();
    }

    @GetMapping("/house/{houseId}")
    @PreAuthorize("hasAuthority('HR')")
    public HouseDetailResponse viewHouseById(@PathVariable Long houseId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size){
        HouseDetail detail = houseService.viewHouseById(houseId, page, size);

        return detail == null ? HouseDetailResponse
                .builder()
                .status(ResponseStatus.builder().success(false).message("House detail not found").build())
                .build():
                HouseDetailResponse
                .builder()
                .status(ResponseStatus.builder().success(true).message("House detail fetched").build())
                .data(detail)
                .build();
    }

    @PostMapping("/house")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseStatus addHouse(@RequestBody NewHouseRequest request){
        System.out.println(request);
        houseService.addHouse(request);
        return ResponseStatus.builder().success(true).message("new house added").build();
    }

    @DeleteMapping("/house/{houseId}")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseStatus deleteHouse(@PathVariable Long houseId){
        houseService.deleteHouse(houseId);
        return ResponseStatus.builder().success(true).message("house deleted").build();
    }
}
