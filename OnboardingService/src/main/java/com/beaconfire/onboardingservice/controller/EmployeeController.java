package com.beaconfire.onboardingservice.controller;
import com.amazonaws.services.xray.model.Http;
import com.beaconfire.onboardingservice.domain.Authentication.RemoteTokenRequest;
import com.beaconfire.onboardingservice.entity.Application.ApplicationStatus;
import com.beaconfire.onboardingservice.entity.Application.ApplicationWorkFlow;
import com.beaconfire.onboardingservice.entity.Application.DigitalDocument;
import com.beaconfire.onboardingservice.entity.Application.DocumentType;
import com.beaconfire.onboardingservice.entity.EmployeeService.Employee;
import com.beaconfire.onboardingservice.entity.common.PersonalDocument;
import com.beaconfire.onboardingservice.entity.common.VisaStatus;
import com.beaconfire.onboardingservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping()
public class EmployeeController {

    private RemoteEmployeeService remoteEmployeeService;
    private RemoteHousingService remoteHousingService;
    private RemoteRegistrationService remoteRegistrationService;
    private RemoteApplicationService remoteApplicationService;
    private FileService fileService;

    @Autowired
    public EmployeeController(RemoteEmployeeService remoteEmployeeService,
                              RemoteHousingService remoteHousingService,
                              RemoteRegistrationService remoteRegistrationService,
                              RemoteApplicationService remoteApplicationService,
                              FileService fileService) {
        this.remoteEmployeeService = remoteEmployeeService;
        this.remoteHousingService = remoteHousingService;
        this.remoteRegistrationService = remoteRegistrationService;
        this.remoteApplicationService = remoteApplicationService;
        this.fileService = fileService;
    }

    @PostMapping("/hr/invite")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseStatus inviteRegistration(@RequestBody String email, HttpServletRequest request){
        RemoteTokenRequest rtr = RemoteTokenRequest.builder().email(email).authorization(request.getHeader("authorization")).build();
        return remoteRegistrationService.invite(rtr);
    }


    @GetMapping("/employee-service/employee/visa-status")
    public ResponseStatus getDocumentForVisaStatus(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        ApplicationWorkFlow application = remoteApplicationService.getApplicationByEmployeeId(jwt);

        if (application == null) {
            return ResponseStatus.builder()
                    .success(false)
                    .message("Application is not found")
                    .build();
        }

        if (application.getApplicationStatus() == ApplicationStatus.REJECTED)
            return ResponseStatus.builder()
                    .success(true)
                    .message(application.getComment())
                    .build();

        Employee employee = remoteEmployeeService.getProfileByUserId(jwt).getData();
        VisaStatus visaStatus = employee.getVisaStatusList().get(employee.getVisaStatusList().size() - 1);
        String visaType = visaStatus.getVisaType();

        if (application.getApplicationStatus() == ApplicationStatus.PENDING) {
            if (visaType.equals("OPT") || visaType.equals("CPT"))
                return ResponseStatus.builder()
                        .success(true)
                        .message("Waiting for HR to approve your OPT/CPT Receipt")
                        .build();
            else
                return ResponseStatus.builder()
                        .success(true)
                        .message("Waiting for HR to approve your " + visaType)
                        .build();
        }


        if (visaType.equals("OPT") || visaType.equals("CPT"))
            return ResponseStatus.builder()
                    .success(true)
                    .message("Please upload a copy of your OPT EAD")
                    .build();
        else if (visaType.equals("OPT EAD"))
            return ResponseStatus.builder()
                    .success(true)
                    .message("Please download and fill out the I-983 form")
                    .build();
        else if (visaType.equals("I-983"))
            return ResponseStatus.builder()
                    .success(true)
                    .message("Please send the I-983 along with all necessary documents to your school and upload the new I-20")
                    .build();
        else if (visaType.equals("I-20"))
            return ResponseStatus.builder()
                    .success(true)
                    .message("Please upload your OPT STEM Receipt")
                    .build();
        else if (visaType.equals("OPT STEM Receipt"))
            return ResponseStatus.builder()
                    .success(true)
                    .message("Please upload your OPT STEM EAD")
                    .build();
        else if (visaType.equals("OPT STEM EAD"))
            return ResponseStatus.builder()
                    .success(true)
                    .message("All documents have been approved")
                    .build();
        else
            return ResponseStatus.builder()
                    .success(false)
                    .message("Invalid document type")
                    .build();
    }

    @PostMapping("/employee-service/employee/visa-status")
    public ResponseStatus uploadDocumentForVisaStatus(HttpServletRequest request, @RequestPart("document") MultipartFile document) throws ParseException {
        String jwt = request.getHeader("Authorization");
        ApplicationWorkFlow application = remoteApplicationService.getApplicationByEmployeeId(jwt);

        if (application == null) {
            return ResponseStatus.builder()
                    .success(false)
                    .message("Application is not found")
                    .build();
        }

        if (application.getApplicationStatus() == ApplicationStatus.REJECTED)
            return ResponseStatus.builder()
                    .success(true)
                    .message(application.getComment())
                    .build();

        Employee employee = remoteEmployeeService.getProfileByUserId(jwt).getData();
        VisaStatus visaStatus = employee.getVisaStatusList().get(employee.getVisaStatusList().size() - 1);
        System.out.println(employee.getVisaStatusList());
        String visaType = visaStatus.getVisaType();
        String description = "";

        if (visaType.equals("OPT") || visaType.equals("CPT"))
            description = "OPT EAD";
        else if (visaType.equals("OPT EAD"))
            description = "I-983";
        else if (visaType.equals("I-983"))
            description = "I-20";
        else if (visaType.equals("I-20"))
            description = "OPT STEM Receipt";
        else if (visaType.equals("OPT STEM Receipt"))
            description = "OPT STEM EAD";
        else if (visaType.equals("OPT STEM EAD"))
            return ResponseStatus.builder()
                    .success(true)
                    .message("All documents have been approved")
                    .build();

        if (application.getApplicationStatus() == ApplicationStatus.PENDING) {
            if (visaType.equals("OPT") || visaType.equals("CPT"))
                return ResponseStatus.builder()
                        .success(true)
                        .message("Waiting for HR to approve your OPT/CPT Receipt")
                        .build();
            return ResponseStatus.builder()
                    .success(true)
                    .message("Waiting for HR to approve your " + visaType)
                    .build();
        }

        DigitalDocument digitalDocument = new DigitalDocument();
        PersonalDocument personalDocument = new PersonalDocument();
        VisaStatus vs = new VisaStatus();

        String docURL = fileService.uploadFile(document);
        digitalDocument.setPath(docURL);
        digitalDocument.setIsRequired(true);
        digitalDocument.setDescription(description);
        digitalDocument.setTitle(document.getOriginalFilename());
        digitalDocument.setType(DocumentType.OPT);
        digitalDocument.setCreateDate(new Date(System.currentTimeMillis()));

        Long docId = remoteApplicationService.saveDigitalDocument(digitalDocument, jwt).getId();
        personalDocument.setId(String.valueOf(docId));
        personalDocument.setPath(docURL);
        personalDocument.setTitle(document.getOriginalFilename());
        personalDocument.setCreateDate(new Date(System.currentTimeMillis()));
        employee.getPersonalDocumentList().add(personalDocument);

        vs.setVisaType(description);
        vs.setActiveFlag(true);
        vs.setStartDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1970"));
        vs.setEndDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1970"));
        vs.setLastModificationDate(new Date(System.currentTimeMillis()));
        employee.getVisaStatusList().add(vs);

        application.setApplicationStatus(ApplicationStatus.PENDING);
        remoteApplicationService.saveApplicationWorkflow(application, jwt);
        remoteEmployeeService.saveOrUpdateEmployee(employee, jwt);

        return ResponseStatus.builder()
                .success(true)
                .message("File uploaded")
                .build();
    }
}


