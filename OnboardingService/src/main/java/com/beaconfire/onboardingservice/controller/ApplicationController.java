package com.beaconfire.onboardingservice.controller;
import com.amazonaws.services.xray.model.Http;
import com.beaconfire.onboardingservice.domain.Application.*;
import com.beaconfire.onboardingservice.domain.EmployeeService.EmployeeResponse;
import com.beaconfire.onboardingservice.domain.HousingService.HouseAssignmentResponse;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.Application.*;
import com.beaconfire.onboardingservice.entity.EmployeeService.Employee;
import com.beaconfire.onboardingservice.entity.common.*;
import com.beaconfire.onboardingservice.service.FileService;
import com.beaconfire.onboardingservice.service.RemoteApplicationService;
import com.beaconfire.onboardingservice.service.RemoteEmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.beaconfire.onboardingservice.service.RemoteHousingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    private RemoteApplicationService remoteApplicationService;
    private RemoteEmployeeService remoteEmployeeService;
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;
    private RemoteHousingManagementService remoteHousingManagementService;
    private FileService fileService;

    @Autowired
    public ApplicationController(RemoteApplicationService remoteApplicationService,
                                 RemoteEmployeeService remoteEmployeeService,
                                RabbitTemplate template, ObjectMapper mapper,
                                 RemoteHousingManagementService remoteHousingManagementService,
                                 FileService fileService) {
        this.remoteApplicationService = remoteApplicationService;
        this.remoteEmployeeService = remoteEmployeeService;
        this.rabbitTemplate = template;
        this.objectMapper = mapper;
        this.remoteHousingManagementService = remoteHousingManagementService;
        this.fileService = fileService;
    }

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('HR')")
    public ApplicationResponse getApplicationByEmployeeId(@PathVariable("employeeId") String employeeId, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");

        EmployeeResponse employeeResponse = remoteEmployeeService.getProfileByUserId(employeeId, jwt);
        if(!employeeResponse.getStatus().getSuccess()) {
            return ApplicationResponse
                    .builder()
                    .status(ResponseStatus
                            .builder()
                            .success(false)
                            .message("application not found")
                            .build())
                    .build();
        }
        Employee employee = employeeResponse.getData();
        DriversLicense driversLicense = employee.getDriverLicense() == null ? null:
                DriversLicense
                        .builder()
                        .number(employee.getDriverLicense())
                        .expirationDate(employee.getDriverLicenseExpiration())
                        .build();


        return ApplicationResponse
                .builder()
                .status(ResponseStatus
                        .builder()
                        .success(true)
                        .message("application retrieved")
                        .build())
                .form(ApplicationForm
                        .builder()
                        .address(employee.getAddressList())
                        .documents(employee.getPersonalDocumentList())
                        .driversLicense(driversLicense)
                        .DOB(Date.from(employee.getDateOfBirth() == null ? null : employee.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        .gender(employee.getGender())
                        .phoneNumber(PhoneNumber
                                .builder()
                                .cell(employee.getCellPhone())
                                .work(employee.getAlternatePhone())
                                .build())
                        .profilePic(employee.getProfilePicture())
                        .SSN(employee.getSsn())
                        .visaStatus(employee.getVisaStatusList())
                        .referenceAndContact(employee.getContactList())
                        .name(Name.builder()
                                .firstname(employee.getFirstName())
                                .lastname(employee.getLastName())
                                .middlename(employee.getMiddleName())
                                .preferredname(employee.getPreferredName())
                                .build())
                        .email(employee.getEmail())
                        .car(remoteApplicationService.findCarsByEmployeeId(employeeId, jwt))
                        .build())
                .build();
    }

    @PostMapping("/accept/{employeeId}")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseStatus accept(@PathVariable String employeeId, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        ApplicationWorkFlow app = remoteApplicationService.getApplicationByEmployeeId(employeeId, jwt);
        if (app == null) {
            return ResponseStatus.builder()
                    .success(false)
                    .message("Application is not found")
                    .build();
        }
        if (app.getApplicationStatus() != ApplicationStatus.PENDING) {
            return ResponseStatus.builder()
                    .success(false)
                    .message("Can only process Pending application")
                    .build();
        }

        app.setApplicationStatus(ApplicationStatus.ACCEPTED);
        HouseAssignmentResponse response = remoteHousingManagementService.assign(employeeId, jwt);
        String housingResponse = response.getStatus().getMessage();
        Long houseId = response.getHouseId();
        if(houseId == null) return ResponseStatus.builder()
                .success(false)
                .message("No house available now")
                .build();

        String msg = "Application status updated to ACCEPTED, " + housingResponse
                + " Your house Id is " + houseId;
        remoteApplicationService.saveApplicationWorkflow(app, jwt);

        return ResponseStatus.builder()
                .success(true)
                .message(msg)
                .build();
    }

    @PostMapping("/reject/{employeeId}")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseStatus reject( @PathVariable String employeeId, @RequestParam String comment, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        ResponseStatus status = remoteApplicationService.rejectApplication(employeeId, comment, jwt);
        if(!status.getSuccess()) return ResponseStatus.builder().success(false).message("application not rejected").build();

        EmployeeResponse employeeResponse = remoteEmployeeService.getProfileByUserId(employeeId, jwt);
        if(!employeeResponse.getStatus().getSuccess()) return ResponseStatus.builder().success(false).message("employee not found").build();

        Employee employee = employeeResponse.getData();
        MQMessage message = MQMessage.builder().invitationLink("localhost:9000/authentication/login/username").email(employee.getEmail()).build();
        try{
            rabbitTemplate.convertAndSend("email.direct","proj3.application.rejected",objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e){
            return ResponseStatus.builder().success(false).message("application not rejected due to failed conversion").build();
        }

        return ResponseStatus.builder().success(true).message("application rejected").build();
    }
}
