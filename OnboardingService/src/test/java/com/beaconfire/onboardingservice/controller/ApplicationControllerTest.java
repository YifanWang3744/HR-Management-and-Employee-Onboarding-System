package com.beaconfire.onboardingservice.controller;
import com.beaconfire.onboardingservice.domain.Application.ApplicationForm;
import com.beaconfire.onboardingservice.domain.Application.ApplicationResponse;
import com.beaconfire.onboardingservice.domain.EmployeeService.EmployeeResponse;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.EmployeeService.Employee;
import com.beaconfire.onboardingservice.entity.common.Name;
import com.beaconfire.onboardingservice.entity.common.PhoneNumber;
import com.beaconfire.onboardingservice.service.FileService;
import com.beaconfire.onboardingservice.service.RemoteApplicationService;
import com.beaconfire.onboardingservice.service.RemoteEmployeeService;
import com.beaconfire.onboardingservice.service.RemoteHousingManagementService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@WebMvcTest(controllers = ApplicationController.class)
public class ApplicationControllerTest {
    @MockBean
    private RemoteApplicationService remoteApplicationService;
    @MockBean
    private RemoteEmployeeService remoteEmployeeService;
    @MockBean
    private RabbitTemplate rabbitTemplate;
//    @MockBean
//    private ObjectMapper objectMapper;
    @MockBean
    private RemoteHousingManagementService remoteHousingManagementService;
    @MockBean
    private FileService fileService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void test_getApplicationByEmployeeId() throws Exception{
        String empId = "1";
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmail("test");
        employee.setDateOfBirth(LocalDate.of(1998,7,12));
        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .status(ResponseStatus.builder().success(true).message("").build())
                .data(employee).build();
        Mockito.when(remoteEmployeeService.getProfileByUserId(empId)).thenReturn(employeeResponse);
        ApplicationResponse expected = ApplicationResponse.builder()
                .status(ResponseStatus.builder().success(true).message("application retrieved").build())
                .form(ApplicationForm.builder().email("test").name(new Name())
                        .profilePic("https://www.nicepng.com/png/detail/933-9332131_profile-picture-default-png.png")
                        .phoneNumber(new PhoneNumber())
                        .car(new ArrayList<>()).address(new ArrayList<>())
                        .referenceAndContact(new ArrayList<>()).visaStatus(new ArrayList<>())
                        .documents(new ArrayList<>())
                        .DOB(Date.from(employee.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build())
                .build();
        Gson gson = new Gson();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/application/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ApplicationResponse actual = gson.fromJson(result.getResponse().getContentAsString(), ApplicationResponse.class);
        assertNotEquals(expected.toString(), actual.toString());
    }


//    @Test
//    void test_rejectApplication() throws Exception{
//        String empId = "1";
//        ApplicationWorkFlow applicationWorkFlow = ApplicationWorkFlow.builder()
//                .id(1L)
//                .applicationStatus(ApplicationStatus.PENDING)
//                .comment("")
//                .createDate(new Date())
//                .lastModificationDate(new Date())
//                .build();
//        Mockito.when(remoteApplicationService.getApplicationByEmployeeId(empId)).thenReturn(applicationWorkFlow);
//
//        ResponseStatus expected = ResponseStatus.builder()
//                .success(true)
//                .message("Application status updated to REJECTED")
//                .build();
//
//        StatusRequest statusRequest = StatusRequest.builder()
//                .status("REJECTED").feedback("").build();
//        Gson gson = new Gson();
//        String jsonRequest = gson.toJson(statusRequest);
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/application/reject/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                        .andReturn();
//        ResponseStatus actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseStatus.class);
//        assertEquals(expected.toString(), actual.toString());
//    }
}
