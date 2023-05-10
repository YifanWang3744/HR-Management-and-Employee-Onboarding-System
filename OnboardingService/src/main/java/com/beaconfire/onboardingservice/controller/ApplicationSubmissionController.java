package com.beaconfire.onboardingservice.controller;
import com.beaconfire.onboardingservice.domain.Application.*;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.Application.*;
import com.beaconfire.onboardingservice.entity.EmployeeService.Employee;
import com.beaconfire.onboardingservice.entity.common.*;
import com.beaconfire.onboardingservice.exception.GeneralInfoException;
import com.beaconfire.onboardingservice.service.FileService;
import com.beaconfire.onboardingservice.service.RemoteApplicationService;
import com.beaconfire.onboardingservice.service.RemoteEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/onboarding")
public class ApplicationSubmissionController {
    private RemoteEmployeeService remoteEmployeeService;
    private RemoteApplicationService remoteApplicationService;
    private FileService fileService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Autowired
    public ApplicationSubmissionController(RemoteEmployeeService remoteEmployeeService,
                                           RemoteApplicationService remoteApplicationService, FileService fileService) {
        this.remoteEmployeeService = remoteEmployeeService;
        this.remoteApplicationService = remoteApplicationService;
        this.fileService = fileService;
    }

    /**
     * redirection on the backend is discouraged in restful. front end and back end should be decoupled.
     * @return
     */

//    @GetMapping("/{employee_id}")
//    public ResponseEntity<String> onboardingRedirect(@PathVariable("employee_id") String id) {
//        Employee e = remoteEmployeeService.getProfileByUserId(id).getData();
//
//        ApplicationWorkFlow application = remoteApplicationService.getApplicationByEmployeeId(e.getId());
//        if (application == null) {
//            return ResponseEntity.ok("Redirecting to application form...");
//        }
//        switch (application.getApplicationStatus()) {
//            case PENDING:
//                return ResponseEntity.ok("Redirecting to your pending application...");
//            case REJECTED:
//                return ResponseEntity.ok("Sorry, your application is rejected. Redirecting to your rejected application...");
//        }
//        return ResponseEntity.ok("Redirecting to home page...");
//    }
    @GetMapping()
    public ResponseStatus getApplicationStatus(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        ApplicationWorkFlow application = remoteApplicationService.getApplicationByEmployeeId(jwt);
        if (application == null)
            return ResponseStatus.builder().success(true).message("application not submitted").build();
        //System.out.println(application.getId());
        return ResponseStatus.builder().success(true).message("application status: " + application.getApplicationStatus().toString()).build();
    }

    @PostMapping("/fill-application")
    public ApplicationFormResponse fillGeneralInfo(HttpServletRequest request,
                                                   @RequestPart("general_info") @Valid GeneralInfo info,
                                                   @RequestPart("profile_pic") MultipartFile profilePic,
                                                   @RequestPart("work_auth") WorkAuth auth,
                                                   @RequestPart("auth_file") MultipartFile authFile,
                                                   @RequestPart("driver") DriversLicenseRequest license,
                                                   @RequestPart("driver_file") MultipartFile driverFile,
                                                   @RequestPart("contact") ReferenceAndContact contactInfo,
                                                   @RequestPart(value = "comment", required = false) String comment) throws GeneralInfoException, ParseException, IOException {

        String jwt = request.getHeader("Authorization");
//        String employeeId =(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee e = remoteEmployeeService.getProfileByUserId(jwt).getData();

        if (info.getName().getFirstname() == null) {
            //throw new GeneralInfoException("Missing Firstname!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Firstname!").build()
                    )
                    .build();
        }
        if (info.getName().getLastname() == null) {
            //throw new GeneralInfoException("Missing Lastname!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Lastname!").build()
                    )
                    .build();
        }
        if (info.getAddress() == null) {
            //throw new GeneralInfoException("Missing Address!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Address!").build()
                    )
                    .build();
        }
        if (info.getPhoneNumber().getCell() == null) {
            //throw new GeneralInfoException("Missing cell phone number!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing cell phone number!").build()
                    )
                    .build();
        }
        if (info.getSsn() == null) {
            //throw new GeneralInfoException("Missing SSN!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing SSN!").build()
                    )
                    .build();
        }
        if (info.getDob() == null) {
            //throw new GeneralInfoException("Missing Date of birth!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Date of birth!").build()
                    )
                    .build();
        }
        e.setFirstName(info.getName().getFirstname());
        e.setLastName(info.getName().getLastname());
        e.setMiddleName(info.getName().getMiddlename());
        e.setPreferredName(info.getName().getPreferredname());

        e.getAddressList().add(info.getAddress());
        e.setCellPhone(String.valueOf(info.getPhoneNumber().getCell()));
        if (info.getPhoneNumber().getWork() != null) {
            e.setAlternatePhone(String.valueOf(info.getPhoneNumber().getWork()));
        }
        if (info.getCar() != null) {
            Car newCar = info.getCar();
            newCar.setEmployeeId(e.getId());
            remoteApplicationService.saveCar(newCar, jwt);
        }
        e.setSsn(info.getSsn());

        e.setDateOfBirth(LocalDate.parse(info.getDob(), formatter));
        if (info.getGender() != null) {
            e.setGender(info.getGender());
        }
        if (info.getPerm() == null) {
            //throw new GeneralInfoException("Please fill visa information!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please fill visa information!").build()
                    )
                    .build();
        }
        if (profilePic != null) {
            String picURL = fileService.uploadFile(profilePic);
            e.setProfilePicture(picURL);
        }

        //fill work auth if not perm
        VisaStatus vs = new VisaStatus();
        //check duplicate
        boolean checkDup = false;
        if (info.getPerm().getStatus().equals("true")) {
            vs.setVisaType(info.getPerm().getType());
        } else {
            DigitalDocument digitalDocument = new DigitalDocument();
            PersonalDocument personalDocument = new PersonalDocument();
            if (auth.getType().equals("CPT") || auth.getType().equals("OPT")) {
                if (authFile == null) {
                    //throw new GeneralInfoException("Please upload your document!");
                    return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please upload your document!").build()
                    )
                    .build();
                }

                String docURL = fileService.uploadFile(authFile);
                digitalDocument.setPath(docURL);
                digitalDocument.setIsRequired(true);
                digitalDocument.setDescription(auth.getType());
                digitalDocument.setTitle(authFile.getOriginalFilename());
                digitalDocument.setType(auth.getType().equals("CPT") ? DocumentType.CPT : DocumentType.OPT);
                digitalDocument.setCreateDate(new Date(System.currentTimeMillis()));
                Long docId = remoteApplicationService.saveDigitalDocument(digitalDocument, jwt).getId();
                personalDocument.setId(String.valueOf(docId));
                personalDocument.setPath(docURL);
                personalDocument.setTitle(authFile.getOriginalFilename());
                personalDocument.setCreateDate(new Date(System.currentTimeMillis()));
                e.getPersonalDocumentList().add(personalDocument);

            } else if (auth.getType() == null || auth.getOther() == null) {
                //throw new GeneralInfoException("Please fill your visa status!");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please fill your visa status!").build()
                    )
                    .build();
            }

            vs.setVisaType(auth.getType() == null ? auth.getOther() : auth.getType());
            vs.setActiveFlag(true);
            vs.setStartDate(new SimpleDateFormat("MM/dd/yyyy").parse(auth.getStartDate()));
            vs.setEndDate(new SimpleDateFormat("MM/dd/yyyy").parse(auth.getEndDate()));
            vs.setLastModificationDate(new Date(System.currentTimeMillis()));

            for (int i = 0; i < e.getVisaStatusList().size(); i++) {
                if (e.getVisaStatusList().get(i).getVisaType().equals(auth.getType())) {
                    e.getVisaStatusList().set(i, vs);
                    checkDup = true;
                    break;
                }
            }
        }
        if (!checkDup) {
            e.getVisaStatusList().add(vs);
        }

        //fill driver's license
        if (license == null) {
            //throw new GeneralInfoException("Please fill driver's license info");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please fill your visa status!").build()
                    )
                    .build();
        }

        if (license.getIsOwn().equals("true")) {
            if (license.getNumber() == null) {
                //throw new GeneralInfoException("Please fill driver's license number");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please fill driver's license number").build()
                    )
                    .build();
            }
            if (license.getExpirationDate() == null) {
                //throw new GeneralInfoException("Please fill driver's license number");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please fill driver's license number").build()
                    )
                    .build();
            }
            if (driverFile == null) {
                //throw new GeneralInfoException("Please upload driver's license");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please upload driver's license").build()
                    )
                    .build();
            }
            e.setDriverLicense(license.getNumber());
            e.setDriverLicenseExpiration(LocalDate.parse(license.getExpirationDate(), formatter));
            DigitalDocument digitalDocumentDD = new DigitalDocument();
            PersonalDocument personalDocumentDD = new PersonalDocument();
            String docURL = fileService.uploadFile(driverFile);
            digitalDocumentDD.setPath(docURL);
            digitalDocumentDD.setIsRequired(true);
            digitalDocumentDD.setDescription("Driver's license");
            digitalDocumentDD.setTitle(driverFile.getOriginalFilename());
            digitalDocumentDD.setType(DocumentType.DRIVERSLICENSE);
            digitalDocumentDD.setCreateDate(new Date(System.currentTimeMillis()));
            Long docId = remoteApplicationService.saveDigitalDocument(digitalDocumentDD, jwt).getId();
            personalDocumentDD.setId(String.valueOf(docId));
            personalDocumentDD.setPath(docURL);
            personalDocumentDD.setTitle(driverFile.getOriginalFilename());
            personalDocumentDD.setCreateDate(new Date(System.currentTimeMillis()));
            e.getPersonalDocumentList().add(personalDocumentDD);

        }

        //fill contact
        if (contactInfo == null) {
            //throw new GeneralInfoException("Please fill in information!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please fill in information!").build()
                    )
                    .build();
        }
        if (contactInfo.getContact() == null) {
            //throw new GeneralInfoException("Please enter at least contact!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Please enter at least contact!").build()
                    )
                    .build();
        }
        if (contactInfo.getReference() != null) {
            if (contactInfo.getReference().getFirstName() == null) {
                //throw new GeneralInfoException("Missing Firstname!");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Firstname!").build()
                    )
                    .build();
            }
            if (contactInfo.getReference().getLastName() == null) {
                //throw new GeneralInfoException("Missing Lastname!");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Lastname!").build()
                    )
                    .build();
            }
            if (contactInfo.getReference().getCellPhone() == null) {
                //throw new GeneralInfoException("Missing cell phone!");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing cell phone!").build()
                    )
                    .build();
            }
            if (contactInfo.getReference().getEmail() == null) {
                //throw new GeneralInfoException("Missing email!");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing email!").build()
                    )
                    .build();
            }
            if (contactInfo.getReference().getRelationship() == null) {
                //throw new GeneralInfoException("Missing relationship!");
                return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing relationship!").build()
                    )
                    .build();
            }
            Contact reference = contactInfo.getReference();
            reference.setType("Reference");
            e.getContactList().add(contactInfo.getReference());
        }

        if (contactInfo.getContact().getFirstName() == null) {
            //throw new GeneralInfoException("Missing Firstname!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Firstname!").build()
                    )
                    .build();
        }
        if (contactInfo.getContact().getLastName() == null) {
            //throw new GeneralInfoException("Missing Lastname!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Firstname!").build()
                    )
                    .build();
        }
        if (contactInfo.getContact().getCellPhone() == null) {
            //throw new GeneralInfoException("Missing cell phone!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing Firstname!").build()
                    )
                    .build();
        }
        if (contactInfo.getContact().getEmail() == null) {
            //throw new GeneralInfoException("Missing email!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing email!").build()
                    )
                    .build();
        }
        if (contactInfo.getContact().getRelationship() == null) {
            //throw new GeneralInfoException("Missing relationship!");
            return ApplicationFormResponse.builder()
                    .status(
                            ResponseStatus.builder()
                            .success(false)
                            .message("Missing relationship!").build()
                    )
                    .build();
        }
        contactInfo.getContact().setType("Contact");
        e.getContactList().add(contactInfo.getContact());

        remoteEmployeeService.saveOrUpdateEmployee(e, jwt);
        ApplicationWorkFlow newSubmission = new ApplicationWorkFlow();
        //newSubmission.setEmployeeId(employeeId);
        newSubmission.setCreateDate(new Date(System.currentTimeMillis()));
        newSubmission.setComment(comment);
        newSubmission.setLastModificationDate(new Date(System.currentTimeMillis()));
        newSubmission.setApplicationStatus(ApplicationStatus.PENDING);

        remoteApplicationService.saveApplicationWorkflow(newSubmission, jwt);
        Employee updatedEmployee = remoteEmployeeService.getProfileByUserId(jwt).getData();
        List<Car> car = remoteApplicationService.findCarsByEmployeeId(jwt);
        return ApplicationFormResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("Please wait for the HR to approve your application.").build()
                )
                .name(
                        Name.builder()
                                .firstname(updatedEmployee.getFirstName())
                                .lastname(updatedEmployee.getLastName())
                                .middlename(updatedEmployee.getMiddleName())
                                .preferredname(updatedEmployee.getPreferredName()).build())
                .profilePic(updatedEmployee.getProfilePicture())
                .address(updatedEmployee.getAddressList())
                .phoneNumber(
                        PhoneNumber.builder()
                                .cell(updatedEmployee.getCellPhone())
                                .work(updatedEmployee.getAlternatePhone() == null ?
                                        null : updatedEmployee.getAlternatePhone())
                                .build()
                )
                .car(car)
                .email(updatedEmployee.getEmail())
                .ssn(updatedEmployee.getSsn())
                .dob(updatedEmployee.getDateOfBirth())
                .gender(updatedEmployee.getGender())
                .visaStatus(updatedEmployee.getVisaStatusList().get(0))
                .driversLicense(
                        DriversLicense.builder()
                                .isOwn(updatedEmployee.getDriverLicense() == null ? "false" : "true")
                                .number(updatedEmployee.getDriverLicense())
                                .expirationDate(updatedEmployee.getDriverLicenseExpiration())
                                .build()
                )
                .referenceAndContact(updatedEmployee.getContactList())
                .documents(updatedEmployee.getPersonalDocumentList())
                .build();


    }

    @GetMapping("/pending")
    public ApplicationFormResponse getPendingApplication(HttpServletRequest request, @RequestBody(required = false) AllDownloadResponse dd) {
        String jwt = request.getHeader("Authorization");
        Employee updatedEmployee = remoteEmployeeService.getProfileByUserId(jwt).getData();
        List<Car> car = remoteApplicationService.findCarsByEmployeeId(jwt);
        if (dd != null) {
            for (DownloadResponse d : dd.getDownloads()) {
                if (d.getDownload().equals("true")) {
                    DigitalDocument doc = remoteApplicationService.findDigitalDocumentByid(Long.parseLong(d.getId()), jwt);
                    String url = doc.getPath();
                    System.out.println(url.substring(url.lastIndexOf('/') + 1));
                    //fileService.downloadFile(url.substring(url.lastIndexOf('/') + 1));
                }
            }
        }

        return ApplicationFormResponse.builder()
                .name(
                        Name.builder()
                                .firstname(updatedEmployee.getFirstName())
                                .lastname(updatedEmployee.getLastName())
                                .middlename(updatedEmployee.getMiddleName())
                                .preferredname(updatedEmployee.getPreferredName()).build())
                .profilePic(updatedEmployee.getProfilePicture())
                .address(updatedEmployee.getAddressList())
                .phoneNumber(
                        PhoneNumber.builder()
                                .cell(updatedEmployee.getCellPhone())
                                .work(updatedEmployee.getAlternatePhone() == null ?
                                        null : updatedEmployee.getAlternatePhone())
                                .build()
                )
                .car(car)
                .email(updatedEmployee.getEmail())
                .ssn(updatedEmployee.getSsn())
                .dob(updatedEmployee.getDateOfBirth())
                .gender(updatedEmployee.getGender())
                .visaStatus(updatedEmployee.getVisaStatusList().get(0))
                .driversLicense(
                        DriversLicense.builder()
                                .isOwn(updatedEmployee.getDriverLicense() == null ? "false" : "true")
                                .number(updatedEmployee.getDriverLicense())
                                .expirationDate(updatedEmployee.getDriverLicenseExpiration())
                                .build()
                )
                .referenceAndContact(updatedEmployee.getContactList())
                .documents(updatedEmployee.getPersonalDocumentList())
                .build();
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = fileService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @PostMapping("/rejected-application")
    public ResponseEntity<String> editApplication(HttpServletRequest request,
                                                  @RequestPart(value = "json", required = false) EditApplicationRequest newSub,
                                                  @RequestPart(value = "drivers_license", required = false) MultipartFile driversLicense,
                                                  @RequestPart(value = "work_auth", required = false) MultipartFile workDoc) {
        String jwt = request.getHeader("Authorization");
        Employee e = remoteEmployeeService.getProfileByUserId(jwt).getData();
        ApplicationWorkFlow oldApp = remoteApplicationService.getApplicationByEmployeeId(jwt);
        if (newSub.getName() != null) {
            e.setFirstName(newSub.getName().getFirstname() == null ? e.getFirstName() : newSub.getName().getFirstname());
            e.setLastName(newSub.getName().getLastname() == null ? e.getLastName() : newSub.getName().getLastname());
            e.setMiddleName(newSub.getName().getMiddlename() == null ? e.getMiddleName() : newSub.getName().getMiddlename());
            e.setPreferredName(newSub.getName().getPreferredname() == null ? e.getPreferredName() : newSub.getName().getPreferredname());
        }
        if (newSub.getProfilePic() != null) {
            e.setProfilePicture(newSub.getProfilePic());
        }
        if (newSub.getPhoneNumber() != null) {
            e.setCellPhone(newSub.getPhoneNumber().getCell() == null ? e.getCellPhone() : newSub.getPhoneNumber().getCell());
            e.setAlternatePhone(newSub.getPhoneNumber().getWork() == null ? e.getAlternatePhone() : newSub.getPhoneNumber().getWork());
        }
        if (newSub.getCar() != null) {
            //TODO update car info
            //if new car passed has an carId to it, then save = update
            remoteApplicationService.saveCar(newSub.getCar(), jwt);
        }
        if (newSub.getSsn() != null) {
            e.setSsn(newSub.getSsn() == null ? e.getSsn() : newSub.getSsn());
        }
        if (newSub.getDob() != null) {
            e.setDateOfBirth(newSub.getDob() == null ? e.getDateOfBirth() : LocalDate.parse(newSub.getDob(), formatter));
        }
        if (newSub.getGender() != null) {
            e.setGender(newSub.getGender() == null ? e.getGender() : newSub.getGender());
        }
        if (newSub.getDriversLicense() != null) {
            e.setDriverLicense(newSub.getDriversLicense().getNumber() == null ?
                    e.getDriverLicense() : newSub.getDriversLicense().getNumber());
            e.setDriverLicenseExpiration(newSub.getDriversLicense().getExpirationDate() == null ?
                    e.getDriverLicenseExpiration() : newSub.getDriversLicense().getExpirationDate());
        }
        if (newSub.getAddress() != null) {
            for (Address add : newSub.getAddress()) {
                for (int i = 0; i < e.getAddressList().size(); i++) {
                    if (e.getAddressList().get(i).getId().equals(add.getId())) {
                        e.getAddressList().set(i, add);
                    }
                    break;
                }
            }
        }

        if (newSub.getReferenceAndContact() != null) {
            for (Contact c : newSub.getReferenceAndContact()) {
                for (int i = 0; i < e.getContactList().size(); i++) {
                    if (e.getContactList().get(i).getId().equals(c.getId())) {
                        e.getContactList().set(i, c);
                    }
                    break;
                }
            }
        }
        if (newSub.getVisaStatus() != null) {
            VisaStatus newVS = newSub.getVisaStatus();
            newVS.setLastModificationDate(new Date(System.currentTimeMillis()));
            e.getVisaStatusList().set(0, newVS);
        }
        if (newSub.getDocuments() != null) {
            //since each application only need to submit at most two documents, assume the size for this is at most 2
            HashMap<String, String> modifiedDoc = newSub.getDocuments();
            if (modifiedDoc.size() == 2) {
                String firstKey = (String) modifiedDoc.keySet().toArray()[0];
                String secondKey = (String) modifiedDoc.keySet().toArray()[1];
                String driverURL = fileService.uploadFile(driversLicense);
                String workURL = fileService.uploadFile(workDoc);
                for (int i = 0; i < e.getPersonalDocumentList().size(); i++) {
                    PersonalDocument oldPd = e.getPersonalDocumentList().get(i);
                    fileService.deleteFile(oldPd.getTitle());
                    if (oldPd.getId().equals(firstKey)) {
                        PersonalDocument newPd = new PersonalDocument();
                        newPd.setId(modifiedDoc.get(0));
                        newPd.setPath(driverURL);
                        newPd.setComment(modifiedDoc.get(firstKey));
                        newPd.setTitle(driversLicense.getOriginalFilename());
                        newPd.setCreateDate(new Date(System.currentTimeMillis()));
                        e.getPersonalDocumentList().set(i, newPd);
                        DigitalDocument oldDoc = remoteApplicationService.findDigitalDocumentByid(Long.valueOf(oldPd.getId()), jwt);
                        oldDoc.setPath(driverURL);
                        oldDoc.setType(DocumentType.DRIVERSLICENSE);
                        oldDoc.setTitle(driversLicense.getOriginalFilename());
                        remoteApplicationService.saveDigitalDocument(oldDoc, jwt);
                    } else if (oldPd.getId().equals(secondKey)) {
                        PersonalDocument newPd = new PersonalDocument();
                        newPd.setId(modifiedDoc.get(1));
                        newPd.setPath(workURL);
                        newPd.setComment(modifiedDoc.get(secondKey));
                        newPd.setTitle(workDoc.getOriginalFilename());
                        newPd.setCreateDate(new Date(System.currentTimeMillis()));
                        e.getPersonalDocumentList().set(i, newPd);
                        DigitalDocument oldDoc = remoteApplicationService.findDigitalDocumentByid(Long.valueOf(oldPd.getId()), jwt);
                        oldDoc.setPath(workURL);
                        //assume type doesn't need to change
                        oldDoc.setTitle(workDoc.getOriginalFilename());
                        remoteApplicationService.saveDigitalDocument(oldDoc, jwt);
                    }
                }

            } else if (driversLicense != null) {
                String firstKey = (String) modifiedDoc.keySet().toArray()[0];
                String driverURL = fileService.uploadFile(driversLicense);
                for (int i = 0; i < e.getPersonalDocumentList().size(); i++) {
                    PersonalDocument oldPd = e.getPersonalDocumentList().get(i);
                    fileService.deleteFile(oldPd.getTitle());
                    if (oldPd.getId().equals(firstKey)) {
                        PersonalDocument newPd = new PersonalDocument();
                        newPd.setId(modifiedDoc.get(0));
                        newPd.setPath(driverURL);
                        newPd.setComment(modifiedDoc.get(firstKey));
                        newPd.setTitle(driversLicense.getOriginalFilename());
                        newPd.setCreateDate(new Date(System.currentTimeMillis()));
                        e.getPersonalDocumentList().set(i, newPd);
                        DigitalDocument oldDoc = remoteApplicationService.findDigitalDocumentByid(Long.valueOf(oldPd.getId()), jwt);
                        oldDoc.setPath(driverURL);
                        //oldDoc.setType(DocumentType.DRIVERSLICENSE);
                        oldDoc.setTitle(driversLicense.getOriginalFilename());
                        remoteApplicationService.saveDigitalDocument(oldDoc, jwt);
                    }
                }
            } else if (workDoc != null) {
                String firstKey = (String) modifiedDoc.keySet().toArray()[0];
                String workURL = fileService.uploadFile(workDoc);
                for (int i = 0; i < e.getPersonalDocumentList().size(); i++) {
                    PersonalDocument oldPd = e.getPersonalDocumentList().get(i);
                    fileService.deleteFile(oldPd.getTitle());
                    if (oldPd.getId() == null) continue;
                    if (oldPd.getId().equals(firstKey)) {
                        PersonalDocument newPd = new PersonalDocument();
                        newPd.setId(modifiedDoc.get(0));
                        newPd.setPath(workURL);
                        newPd.setComment(modifiedDoc.get(firstKey));
                        newPd.setTitle(workDoc.getOriginalFilename());
                        newPd.setCreateDate(new Date(System.currentTimeMillis()));
                        e.getPersonalDocumentList().set(i, newPd);
                        DigitalDocument oldDoc = remoteApplicationService.findDigitalDocumentByid(Long.valueOf(oldPd.getId()), jwt);
                        oldDoc.setPath(workURL);
                        //oldDoc.setType(DocumentType.DRIVERSLICENSE);
                        oldDoc.setTitle(workDoc.getOriginalFilename());
                        remoteApplicationService.saveDigitalDocument(oldDoc, jwt);
                    }
                }
            }

        }
        remoteEmployeeService.saveOrUpdateEmployee(e, jwt);
        oldApp.setApplicationStatus(ApplicationStatus.PENDING);
        oldApp.setLastModificationDate(new Date(System.currentTimeMillis()));
        remoteApplicationService.saveApplicationWorkflow(oldApp, jwt);
        return ResponseEntity.ok("Document resubmit successfully");

    }
}
