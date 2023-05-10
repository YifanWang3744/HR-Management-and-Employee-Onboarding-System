package com.bf.housing.controller;

import com.bf.housing.domain.request.NewHouseRequest;
import com.bf.housing.domain.response.HousingDetailsResponse;
import com.bf.housing.entity.Employee;
import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.domain.response.HouseListResponse;
import com.bf.housing.domain.response.HouseResponse;
import com.bf.housing.entity.House;
import com.bf.housing.entity.Roommate;
import com.bf.housing.exception.AccessDeniedException;
import com.bf.housing.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("")
public class HouseController {

    private HouseService houseService;

    @Autowired
    HouseController(HouseService houseService) {
        this.houseService = houseService;

    }

    @GetMapping("/house/{houseId}")
    HouseResponse getHouseById(@PathVariable Long houseId){
        House house = houseService.findHouseById(houseId);


        return HouseResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .success(true)
                                .message("Get house by houseId")
                                .build()
                )
                .house(house)
                .build();
    }

    @GetMapping("houses")
    HouseListResponse getAllHouses(){
        List<House> houseList = houseService.findAll();

        return HouseListResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .success(true)
                                .message("Get all houses")
                                .build()
                )
                .houseList(houseList)
                .build();
    }
}
