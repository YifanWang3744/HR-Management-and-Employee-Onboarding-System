package com.team3.employeeservice.controller;

import com.team3.employeeservice.domain.Employee;
import com.team3.employeeservice.domain.PersonalDocument;
import com.team3.employeeservice.exception.EmployeeNotFoundException;
import com.team3.employeeservice.request.PersonalDocumentRequest;
import com.team3.employeeservice.response.PersonalDocumentListResponse;
import com.team3.employeeservice.response.ResponseStatus;
import com.team3.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/personal-document")
public class PersonalDocumentController {

    private EmployeeService employeeService;

    @Autowired
    public PersonalDocumentController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping()
    public PersonalDocumentListResponse getAllPersonalDocuments() throws EmployeeNotFoundException {
        String employee_id = employeeService.getCurrentEmployeeId();

        return PersonalDocumentListResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("get personal documents")
                        .build())
                .personalDocumentList(employeeService.findEmployeeById(employee_id).getPersonalDocumentList())
                .build();
    }


    @PostMapping("")
    public PersonalDocumentListResponse addPersonalDocument(@Valid @RequestBody PersonalDocumentRequest personalDocumentRequest,
                                                            BindingResult bindingResult) throws EmployeeNotFoundException {

        String employee_id = employeeService.getCurrentEmployeeId();

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(error -> System.out.println(
                    "ValidationError in " + error.getObjectName() + ": " + error.getDefaultMessage()));
            return PersonalDocumentListResponse.builder()
                    .status(
                            ResponseStatus.builder()
                                    .success(false)
                                    .message("Validation error")
                                    .build()
                    )
                    .build();
        }

        PersonalDocument personalDocument = PersonalDocument.builder()
                .id(personalDocumentRequest.getId())
                .path(personalDocumentRequest.getPath())
                .title(personalDocumentRequest.getTitle())
                .comment(personalDocumentRequest.getComment())
                .createDate(new Date(System.currentTimeMillis()))
                .build();
        
        Employee employee = employeeService.findEmployeeById(employee_id);

        if (employee.getPersonalDocumentList() == null)
            employee.setPersonalDocumentList(new ArrayList<>());
        employee.getPersonalDocumentList().add(personalDocument);
        employeeService.saveOrUpdateEmployee(employee);

        return PersonalDocumentListResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Personal document added.")
                        .build())
                .personalDocumentList(employee.getPersonalDocumentList())
                .build();
    }
}
