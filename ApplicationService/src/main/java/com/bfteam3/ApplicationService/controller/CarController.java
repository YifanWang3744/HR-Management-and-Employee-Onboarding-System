//package com.bfteam3.ApplicationService.controller;
//
//
//import com.bfteam3.ApplicationService.domain.common.Car;
//import com.bfteam3.ApplicationService.domain.response.AllCarsResponse;
//import com.bfteam3.ApplicationService.domain.response.ResponseStatus;
//import com.bfteam3.ApplicationService.service.CarService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/car")
//public class CarController {
//
//    private CarService carService;
//
//    @Autowired
//    public void setCarService(CarService carService) {
//        this.carService = carService;
//    }
//
////    @GetMapping("/{empId}")
////    public AllCarsResponse findCarsByEmployeeId( @PathVariable String empId){
////        List<Car> cars = carService.findCarsByEmployeeId(empId);
////        return AllCarsResponse
////                .builder()
////                .status(ResponseStatus.builder().success(true).message("cars found for current employee").build())
////                .cars(cars)
////                .build();
////    }
////
////    @GetMapping()
////    public AllCarsResponse findCarsByEmployeeId(){
////        String empId = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////        List<Car> cars = carService.findCarsByEmployeeId(empId);
////        return AllCarsResponse
////                .builder()
////                .status(ResponseStatus.builder().success(true).message("cars found for current employee").build())
////                .cars(cars)
////                .build();
////    }
//}
