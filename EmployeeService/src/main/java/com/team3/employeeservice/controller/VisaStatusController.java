package com.team3.employeeservice.controller;

import com.team3.employeeservice.domain.Employee;
import com.team3.employeeservice.domain.VisaStatus;
import com.team3.employeeservice.exception.EmployeeNotFoundException;
import com.team3.employeeservice.request.VisaStatusRequest;
import com.team3.employeeservice.response.PersonalDocumentListResponse;
import com.team3.employeeservice.response.ResponseStatus;
import com.team3.employeeservice.response.VisaStatusListResponse;
import com.team3.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sun.awt.EmbeddedFrame;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/visa-status")
public class VisaStatusController {

    private EmployeeService employeeService;

    @Autowired
    public VisaStatusController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("")
    public VisaStatusListResponse addVisaStatus(@Valid @RequestBody VisaStatusRequest visaStatusRequest,
                                                BindingResult bindingResult) throws ParseException, EmployeeNotFoundException {

        String employee_id = employeeService.getCurrentEmployeeId();

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(error -> System.out.println(
                    "ValidationError in " + error.getObjectName() + ": " + error.getDefaultMessage()));
            return VisaStatusListResponse.builder()
                    .status(
                            ResponseStatus.builder()
                                    .success(false)
                                    .message("Validation error")
                                    .build()
                    )
                    .build();
        }

        VisaStatus visaStatus = VisaStatus.builder()
                .visaType(visaStatusRequest.getVisaType())
                .activeFlag(true)
                .startDate(new SimpleDateFormat("MM/dd/yyyy").parse(visaStatusRequest.getStartDate()))
                .endDate(new SimpleDateFormat("MM/dd/yyyy").parse(visaStatusRequest.getEndDate()))
                .lastModificationDate(new Date(System.currentTimeMillis()))
                .build();

        Employee employee = employeeService.findEmployeeById(employee_id);
        if (employee.getVisaStatusList() == null)
            employee.setVisaStatusList(new ArrayList<>());
        employee.getVisaStatusList().add(visaStatus);
        employeeService.saveOrUpdateEmployee(employee);

        return VisaStatusListResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Visa status added.")
                        .build())
                .visaStatusList(employee.getVisaStatusList())
                .build();
    }

}
