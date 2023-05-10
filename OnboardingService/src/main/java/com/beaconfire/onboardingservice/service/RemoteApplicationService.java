package com.beaconfire.onboardingservice.service;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.Application.ApplicationWorkFlow;
import com.beaconfire.onboardingservice.entity.Application.Car;
import com.beaconfire.onboardingservice.entity.Application.DigitalDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("application-service")
public interface RemoteApplicationService {

    @GetMapping("/application-service/applicationworkflow")
    ApplicationWorkFlow getApplicationByEmployeeId(@RequestHeader(name = "authorization") String jwt);

    @GetMapping("/application-service/applicationworkflow/{employeeId}")
    ApplicationWorkFlow getApplicationByEmployeeId(@PathVariable String employeeId, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/application-service/digitaldocument/{id}")
    DigitalDocument findDigitalDocumentByid(@PathVariable("id") Long id, @RequestHeader(name = "authorization") String jwt);

    @PostMapping("/application-service/applicationworkflow")
    ApplicationWorkFlow saveApplicationWorkflow(@RequestBody ApplicationWorkFlow submission, @RequestHeader(name = "authorization") String jwt);

    @PostMapping("/application-service/digitaldocument")
    DigitalDocument saveDigitalDocument(@RequestBody DigitalDocument doc, @RequestHeader(name = "authorization") String jwt);

    @PostMapping("/application-service/car")
    Car saveCar(@RequestBody Car newCar, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/application-service/car/{empId}")
    List<Car> findCarsByEmployeeId(@PathVariable String empId, @RequestHeader(name = "authorization") String jwt);

    @GetMapping("/application-service/car")
    List<Car> findCarsByEmployeeId(@RequestHeader(name = "authorization") String jwt);

    @PostMapping("/application-service/review/reject/{empId}")
    ResponseStatus rejectApplication(@PathVariable String empId, @RequestParam String comment,  @RequestHeader(name = "authorization") String jwt);
}
