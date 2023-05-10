package com.bf.housing.controller;

import com.bf.housing.entity.House;
import com.bf.housing.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private HouseService service;

    @GetMapping("/test")
    public House test(){
        House house = service.addHouse();
        System.out.println(house.getLandlord());
        System.out.println(house.getFacilities());
        return house;
    }

//    @GetMapping("/houses")
//    public List<House> getAll() {
//        return service.findAllHouses();
//    }
}
