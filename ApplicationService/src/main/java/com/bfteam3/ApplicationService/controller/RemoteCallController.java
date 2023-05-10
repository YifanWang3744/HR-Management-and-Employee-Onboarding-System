package com.bfteam3.ApplicationService.controller;
import com.bfteam3.ApplicationService.domain.common.Car;
import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
import com.bfteam3.ApplicationService.domain.entity.Application.DigitalDocument;
import com.bfteam3.ApplicationService.service.ApplicationWorkflowService;
import com.bfteam3.ApplicationService.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * this controller is for remote call purposes
 */

@RestController
@RequestMapping()
public class RemoteCallController {

    private ApplicationWorkflowService applicationWorkFlowService;
    private CarService carService;

    @Autowired
    public RemoteCallController(CarService carService,
                                ApplicationWorkflowService applicationWorkFlowService) {
        this.applicationWorkFlowService = applicationWorkFlowService;
        this.carService = carService;
    }

    @GetMapping("/applicationworkflow/{empId}")
    public ApplicationWorkFlow getApplicationByEmployeeId(@PathVariable String empId){
        return applicationWorkFlowService.getApplicationByEmployeeId(empId);
    }

    @GetMapping("/digitaldocument/{id}")
    public DigitalDocument findDigitalDocumentByid(@PathVariable("id") Long id){
        return applicationWorkFlowService.findDigitalDocumentByid(id);
    }

    @PostMapping("/applicationworkflow")
    public ApplicationWorkFlow saveApplicationWorkflow(@RequestBody ApplicationWorkFlow submission){
        String employeeId =(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        submission.setEmployeeId(employeeId);
        return applicationWorkFlowService.saveApplicationWorkflow(submission);
    }

    @PostMapping("/digitaldocument")
    public DigitalDocument saveDigitalDocument(@RequestBody DigitalDocument doc){
        return applicationWorkFlowService.saveDigitalDocument(doc);
    }

    @PostMapping("/car")
    public Car saveCar(@RequestBody Car newCar){
        return carService.saveCar(newCar);
    }

    @GetMapping("/car")
    public List<Car> findCarByEmployeeId(){
        String employeeId =(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return carService.findCarsByEmployeeId(employeeId);
    }

    @GetMapping("/car/{empId}")
    public List<Car> findCarByEmployeeId(@PathVariable String empId){
        return carService.findCarsByEmployeeId(empId);
    }
