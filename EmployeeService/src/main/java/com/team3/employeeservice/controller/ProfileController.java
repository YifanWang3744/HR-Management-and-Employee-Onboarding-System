package com.team3.employeeservice.controller;

import com.team3.employeeservice.domain.*;
import com.team3.employeeservice.exception.EmployeeNotFoundException;
import com.team3.employeeservice.request.*;
import com.team3.employeeservice.response.*;
import com.team3.employeeservice.response.ResponseStatus;
import com.team3.employeeservice.resultWrapper.ProfileWrapper;
import com.team3.employeeservice.resultWrapper.StatusWrapper;
import com.team3.employeeservice.service.EmployeeService;
import com.team3.employeeservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final EmployeeService service;
    private final FileService fileService;

    @Autowired
    public ProfileController(EmployeeService service, FileService fileService) {
        this.service = service;
        this.fileService = fileService;
    }

    @GetMapping("/employees")
    public EmployeesResponse getAllProfiles() {
        List<Employee> employees = service.findAllEmployees();

        return EmployeesResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Get all employees' profiles").build())
                .data(employees)
                .build();
    }

    @GetMapping("/employee")
    @PreAuthorize("permitAll()")
    public EmployeeResponse getProfileByUserId() {
        String employee_id = service.getCurrentEmployeeId();
        Employee employee;
        try {
            employee = service.findEmployeeById(employee_id);
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
            return EmployeeResponse.builder()
                    .status(ResponseStatus.builder().success(false).message("Get current employee's profile.").build())
                    .build();
        }

        return EmployeeResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Get current employee's profile.").build())
                .data(employee).build();
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("permitAll()")
    public EmployeeResponse getProfileByUserId(@PathVariable String employeeId) {
        Employee employee;
        try {
            employee = service.findEmployeeById(employeeId);
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
            return EmployeeResponse.builder()
                    .status(ResponseStatus.builder().success(false).message("Get current employee's profile.").build())
                    .build();
        }

        return EmployeeResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Get current employee's profile.").build())
                .data(employee).build();
    }

    @GetMapping("/visa-status")
    public VisaStatusListResponse getVisaStatusListByUserId() throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee employee = service.findEmployeeById(employee_id);

        return VisaStatusListResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Get current employee's visa status list.").build())
                .visaStatusList(employee.getVisaStatusList()).build();
    }

//    @PatchMapping("/employee/{employee_id}")
//    public EmployeeResponse saveOrUpdateEmployee(@PathVariable("employee_id") String id, @RequestBody Employee employee) throws EmployeeNotFoundException {
//        Employee e = service.findEmployeeById(id);
//        employee.setId(id);
//        service.saveOrUpdateEmployee(employee);
//
//        return EmployeeResponse.builder()
//                .status(ResponseStatus.builder().success(true).message("Employee saved").build())
//                .data(employee)
//                .build();
//    }

    @PatchMapping("/name")
    public EmployeeResponse editName(@Valid @RequestBody EmployeeNameRequest request) throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee updatedEmployee = service.findEmployeeById(employee_id);

        updatedEmployee.setFirstName(request.getFirstName());
        updatedEmployee.setLastName(request.getLastName());
        updatedEmployee.setMiddleName(request.getMiddleName());
        updatedEmployee.setPreferredName(request.getPreferredName());
        updatedEmployee.setProfilePicture(request.getProfilePicture());
        updatedEmployee.setEmail(request.getEmail());
        updatedEmployee.setSsn(request.getSsn());
        updatedEmployee.setDateOfBirth(request.getDateOfBirth());
        updatedEmployee.setGender(request.getGender());

        service.saveOrUpdateEmployee(updatedEmployee);

        return EmployeeResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Employee profile updated").build())
                .data(updatedEmployee)
                .build();
    }

    @PostMapping("/address")
    public ResponseStatus addNewAddress() throws EmployeeNotFoundException {
        // get current employee
        String employee_id = service.getCurrentEmployeeId();
        Employee employee = service.findEmployeeById(employee_id);

        // get address list
        List<Address> addressList = employee.getAddressList();
        String address_id = addressList.isEmpty() ? "1" : String.valueOf(addressList.size() + 1);

        // create a new address
        addressList.add(Address.builder().id(address_id).build());
        employee.setAddressList(addressList);
        service.saveOrUpdateEmployee(employee);

        return ResponseStatus.builder().success(true).message("New address id=" + address_id + " created successfully").build();
    }

    @PatchMapping("/address")
    public EmployeeResponse editAddress(@Valid @RequestBody Address request) throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee updatedEmployee = service.findEmployeeById(employee_id);

        // get current address list
        List<Address> addressList = updatedEmployee.getAddressList();
        if (addressList.isEmpty()) {
            addressList.add(Address.builder().id("1").build());
        }

        for (Address address : updatedEmployee.getAddressList()) {
            if (address.getId().equals(request.getId())) {

                address.setAddressLine1(request.getAddressLine1());
                address.setAddressLine2(request.getAddressLine2());
                address.setCity(request.getCity());
                address.setState(request.getState());
                address.setZipCode(request.getZipCode());

                service.saveOrUpdateEmployee(updatedEmployee);

                return EmployeeResponse.builder()
                        .status(ResponseStatus.builder().success(true).message("Address updated").build())
                        .data(updatedEmployee)
                        .build();
            }
        }

        // if address_id not found
        return EmployeeResponse.builder().status(ResponseStatus.builder().success(false).message("Address id=" + request.getId() + " not found!").build()).build();
    }

    @PatchMapping("/contact-info")
    public EmployeeResponse editContactInfo(@Valid @RequestBody ContactInfoRequest request) throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee updatedEmployee = service.findEmployeeById(employee_id);

        updatedEmployee.setCellPhone(request.getCellPhone());
        updatedEmployee.setAlternatePhone(request.getWorkPhone());

        service.saveOrUpdateEmployee(updatedEmployee);
        return EmployeeResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Contact info updated").build())
                .data(updatedEmployee).build();
    }

    @PatchMapping("/employment")
    public EmployeeResponse editEmployment(@Valid @RequestBody EmploymentRequest request) throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee updatedEmployee = service.findEmployeeById(employee_id);

        updatedEmployee.setStartDate(request.getStartDate());
        updatedEmployee.setEndDate(request.getEndDate());

        service.saveOrUpdateEmployee(updatedEmployee);
        return EmployeeResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Employment info updated").build())
                .data(updatedEmployee).build();
    }

    @PostMapping("/emergency-contact")
    public ResponseStatus addNewEmergencyContact() throws EmployeeNotFoundException {
        // get current employee
        String employee_id = service.getCurrentEmployeeId();
        Employee employee = service.findEmployeeById(employee_id);

        // get emergency contact list
        List<Contact> contactList = employee.getContactList();
        String contact_id = contactList.isEmpty() ? "1" : String.valueOf(contactList.size() + 1);

        // create a new address
        contactList.add(Contact.builder().id(contact_id).build());
        employee.setContactList(contactList);
        service.saveOrUpdateEmployee(employee);

        return ResponseStatus.builder().success(true).message("New contact id=" + contact_id + " created successfully").build();
    }

    @PatchMapping("/emergency-contact")
    public EmployeeResponse editEmergencyContact(@Valid @RequestBody Contact request) throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee updatedEmployee = service.findEmployeeById(employee_id);

        for (Contact c : updatedEmployee.getContactList()) {
            if (c.getId().equals(request.getId())) {

                c.setFirstName(request.getFirstName());
                c.setLastName(request.getLastName());
                c.setMiddleName(request.getMiddleName());
                c.setCellPhone(request.getCellPhone());
                c.setAlternatePhone(request.getAlternatePhone());
                c.setEmail(request.getEmail());
                c.setRelationship(request.getRelationship());
                c.setType(request.getType());

                service.saveOrUpdateEmployee(updatedEmployee);
                return EmployeeResponse.builder()
                        .status(ResponseStatus.builder().success(true).message("Emergency contact updated").build())
                        .data(updatedEmployee).build();
            }
        }
        return EmployeeResponse.builder().status(ResponseStatus.builder().success(false).message("Contact id=" + request.getId() + " not found").build()).build();
    }

    @PostMapping("/documents")
    public ResponseStatus addNewPersonalDocument() throws EmployeeNotFoundException {
        // get current employee
        String employee_id = service.getCurrentEmployeeId();
        Employee employee = service.findEmployeeById(employee_id);

        // get document list
        List<PersonalDocument> documents = employee.getPersonalDocumentList();
        String document_id = documents.isEmpty() ? "1" : String.valueOf(documents.size() + 1);

        // create a new address
        documents.add(PersonalDocument.builder().id(document_id).build());
        employee.setPersonalDocumentList(documents);
        service.saveOrUpdateEmployee(employee);

        return ResponseStatus.builder().success(true).message("New document id=" + document_id + " created successfully").build();
    }

    @DeleteMapping("/document/{document_id}")
    public ResponseStatus deletePersonalDocument(@PathVariable String document_id) throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee employee = service.findEmployeeById(employee_id);
        for (PersonalDocument d : employee.getPersonalDocumentList()) {
            if (d.getId().equals(document_id)) {
                try {
                    String urlString = d.getPath();
                    URL url = new URL(urlString);
                    String fileName = new File(url.getPath()).getName();

                    fileService.deleteFile(fileName);
                    d.setPath("");
                    service.saveOrUpdateEmployee(employee);
                    return ResponseStatus.builder().success(true).message("Changes discarded").build();
                } catch (MalformedURLException e) {
                    return ResponseStatus.builder().success(false).message("Download failed").build();
                }
            }
        }
        return ResponseStatus.builder().success(false).message("Document not found").build();
    }

    @GetMapping("/document/{document_id}")
    public ResponseEntity<ByteArrayResource> downloadPersonalDocument(@PathVariable String document_id) throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee employee = service.findEmployeeById(employee_id);
        for (PersonalDocument d : employee.getPersonalDocumentList()) {
            if (d.getId().equals(document_id)) {
                String path = d.getPath();
                URI uri = null;
                try {
                    uri = new URI(path);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                String fileName = new File(uri.getPath()).getName();

                byte[] data = fileService.downloadFile(fileName);
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity
                        .ok()
                        .contentLength(data.length)
                        .header("Content-type", "application/octet-stream")
                        .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                        .body(resource);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/documents")
    public EmployeeResponse editPersonalDocument(@RequestPart("request") PersonalDocumentRequest request,
                                                 @RequestPart("file") MultipartFile file) throws EmployeeNotFoundException {
        String employee_id = service.getCurrentEmployeeId();
        Employee updatedEmployee = service.findEmployeeById(employee_id);
        String url = fileService.uploadFile(file);
        System.out.println(url);

        for (PersonalDocument d : updatedEmployee.getPersonalDocumentList()) {
            if (d.getId().equals(request.getId())) {
                // if the file already exists, then delete it
                if (d.getPath() != null && !d.getPath().isEmpty())
                    fileService.deleteFile(d.getPath());
                d.setPath(url);
                d.setCreateDate(new Date(System.currentTimeMillis()));
                d.setTitle(request.getTitle());
                d.setComment(request.getComment());

                service.saveOrUpdateEmployee(updatedEmployee);
                return EmployeeResponse.builder()
                        .status(ResponseStatus.builder().success(true).message("Documents updated").build())
                        .data(updatedEmployee).build();
            }
        }

        return EmployeeResponse.builder().status(ResponseStatus.builder().success(false).message("document id not found").build()).build();
    }


    /**
     * HR status tracking table
     */
    @GetMapping("/visa/status")
    @PreAuthorize("hasAuthority('HR')")
    public StatusesResponse getVisaStatuses(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "2") int size) {

        List<StatusWrapper> visaStatuses = service.getVisaStatuses(page, size);

        return StatusesResponse
                .builder()
                .visaStatusList(visaStatuses)
                .status(ResponseStatus.builder().success(true).message("Status tracking").build())
                .build();
    }


    @GetMapping("/summary")
    @PreAuthorize("hasAuthority('HR')")
    public SummariesResponse getAllSummaries(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size) {
        List<ProfileWrapper> summaries = service.getAllSummaries(page, size);

        return SummariesResponse.builder()
                .status(ResponseStatus.builder().success(true).message("employee summaries fetched").build())
                .summaryList(summaries)
                .build();
    }

    @GetMapping("/summary/email")
    @PreAuthorize("hasAuthority('HR')")
    public SummariesResponse getSummariesByEmail(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size, @RequestParam String email) {
        List<ProfileWrapper> summaries = service.getSummariesByEmail(page, size, email);

        return SummariesResponse.builder()
                .status(ResponseStatus.builder().success(true).message("employee summaries fetched").build())
                .summaryList(summaries)
                .build();
    }

    @GetMapping("/summary/name")
    @PreAuthorize("hasAuthority('HR')")
    public SummariesResponse getSummariesByName(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "2") int size, @RequestParam String firstName, @RequestParam String lastName) {
        List<ProfileWrapper> summaries = service.getSummariesByName(page, size, firstName, lastName);

        return SummariesResponse.builder()
                .status(ResponseStatus.builder().success(true).message("employee summaries fetched").build())
                .summaryList(summaries)
                .build();
    }

    @PostMapping("/employee")
    public ResponseStatus saveOrUpdateEmployee(@RequestBody Employee employee){
        String employee_id = service.getCurrentEmployeeId();
        employee.setId(employee_id);
        service.saveOrUpdateEmployee(employee);
        return ResponseStatus.builder().success(true).message("employee saved or updated ").build();
    }

    @PostMapping("/house/new/{empId}")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseStatus updateEmployeeHouseInfo(@PathVariable String empId, @RequestParam Long houseId) {
        Employee employee;
        try{
            employee = service.findEmployeeById(empId);
        } catch (EmployeeNotFoundException e){
            return ResponseStatus.builder().success(false).message("employee house info not updated ").build();
        }
        employee.setHouseId(houseId);
        service.saveOrUpdateEmployee(employee);
        return ResponseStatus.builder().success(true).message("employee house info updated ").build();
    }

    @PostMapping("/house")
    @PreAuthorize("hasAuthority('HR')")
    @Transactional
    public ResponseStatus removeEmployeesHouseInfo(@RequestParam Long houseId) {
        List<Employee> employees = service.findEmployeesByHouseId(houseId);
        employees.forEach(e->e.setHouseId(null));
        employees.forEach(e->service.saveOrUpdateEmployee(e));
        return ResponseStatus.builder().success(true).message("employee house info updated ").build();
    }

    @GetMapping("/occupants/{houseId}")
    @PreAuthorize("permitAll()")
    public OccupantResponse getOccupantsByHouseId(@PathVariable Long houseId){
        Integer res = service.getOccupantsByHouseId(houseId);

        return OccupantResponse
                .builder()
                .status(ResponseStatus.builder().success(true).message("occupant num queried").build())
                .num(res)
                .build();
    }

    @GetMapping("/occupants/detail/{houseId}")
    @PreAuthorize("permitAll()")
    public EmployeesResponse getOccupantDetailsByHouseId(@PathVariable Long houseId){
        List<Employee> employees = service.findEmployeesByHouseId(houseId);
        return EmployeesResponse.builder()
                .status(ResponseStatus.builder().success(true).message("employees queried").build())
                .data(employees)
                .build();
    }
}
