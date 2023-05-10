package com.team3.employeeservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team3.employeeservice.domain.*;
import com.team3.employeeservice.exception.EmployeeNotFoundException;
import com.team3.employeeservice.request.ContactInfoRequest;
import com.team3.employeeservice.request.EmployeeNameRequest;
import com.team3.employeeservice.request.EmploymentRequest;
import com.team3.employeeservice.response.EmployeeResponse;
import com.team3.employeeservice.response.ResponseStatus;
import com.team3.employeeservice.response.VisaStatusListResponse;
import com.team3.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = ProfileController.class)
public class ProfileControllerTest {
    @MockBean
    private EmployeeService service;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_getProfileByUserId_success() throws Exception {
        Employee employee = new Employee("1", 1L, "Peppa", "Pig", null,
                null, null, "peppa@piggy.com", "1112223344",
                null, "Female", null, null,
                null, null, null, null, null,
                null, null, null, new ArrayList<>());

        Mockito.when(service.findEmployeeById("1")).thenReturn(employee);

        EmployeeResponse expected = EmployeeResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Get current employee's profile.").build())
                .data(employee)
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/profile/employee/{employee_id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Gson gson = new Gson();
        EmployeeResponse actual = gson.fromJson(result.getResponse().getContentAsString(), EmployeeResponse.class);
        System.out.println(actual.toString());
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void test_getProfileByUserId_fail() throws Exception {
        Mockito.when(service.findEmployeeById("1")).thenThrow(new EmployeeNotFoundException("Employee with id=1 Not Found!"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/profile/employee/{employee_id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ResponseStatus expected = ResponseStatus.builder().success(false).message("Employee with id=1 Not Found!").build();

        Gson gson = new Gson();
        ResponseStatus actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseStatus.class);
//        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void test_getVisaStatusListByUserId() throws Exception {
        Employee e = new Employee();
        e.setId("1");
        List<VisaStatus> expected = Arrays.asList(
                new VisaStatus(
                        "1",
                        "H1B",
                        true,
                        null,
                        new Date(System.currentTimeMillis()+1000*3600*24*200),
                        null)
        );
        e.setVisaStatusList(expected);
        System.out.println(e);
        Mockito.when(service.findEmployeeById("1")).thenReturn(e);

        Gson gson = new Gson();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/profile/employee/{employee_id}/visa-status", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        VisaStatusListResponse actual = gson.fromJson(result.getResponse().getContentAsString(), VisaStatusListResponse.class);
        assertEquals(expected.toString(), actual.getVisaStatusList().toString());
    }

//    @Test
//    void test_getHouseByUserId() throws Exception {
//        Employee e = new Employee();
//        e.setHouseId(1L);
//        Mockito.when(service.findEmployeeById("1")).thenReturn(e);
//
//        House h = new House();
//        h.setId(1L);
//        HouseResponse expected = HouseResponse.builder().house(h).build();
//        Mockito.when(housingService.getHouseById(1L)).thenReturn(expected);
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/profile/employee/{employee_id}/housing", "1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        Gson gson = new Gson();
//        HouseResponse actual = gson.fromJson(result.getResponse().getContentAsString(), HouseResponse.class);
//        assertEquals(h.toString(), actual.getHouse().toString());
//    }

    @Test
    void test_editName() throws Exception {
        EmployeeNameRequest request = new EmployeeNameRequest("Rebecca", "Rabbit",
                null, null, null, "rebecca@rabbit.com",
                "4443332211", null, "Female");

        Employee employee = new Employee("1", 1L, "Peppa", "Pig", null,
                null, null, "peppa@piggy.com", "1112223344",
                null, "Female", null, null,
                null, null, null, null, null,
                null, null, null, null);

        Mockito.when(service.findEmployeeById("1")).thenReturn(employee);

        EmployeeResponse expected = EmployeeResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Employee profile updated").build())
                .data(employee).build();

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/profile/employee/{employee_id}/name", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                .andReturn();
        EmployeeResponse actual = gson.fromJson(result.getResponse().getContentAsString(), EmployeeResponse.class);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void test_editAddress() throws Exception {
        Address request = new Address(
                "1",
                "110 Fifth Ave",
                "Apt 110",
                "City",
                "State",
                "10010");
        Employee employee = new Employee();
        employee.setId("1");
        employee.setAddressList(Arrays.asList(new Address("1", null, null, null, null, null)));
        Mockito.when(service.findEmployeeById("1")).thenReturn(employee);

        employee.setAddressList(Arrays.asList(request));
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/profile/employee/{employee_id}/address", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andReturn();
        EmployeeResponse actual = gson.fromJson(result.getResponse().getContentAsString(), EmployeeResponse.class);
        assertEquals(employee.getAddressList().toString(), actual.getData().getAddressList().toString());
    }

    @Test
    void test_editContactInfo() throws Exception {
        ContactInfoRequest request = new ContactInfoRequest();
        request.setCellPhone("1112223344");
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);

        Employee employee = new Employee();
        employee.setId("1");
        Mockito.when(service.findEmployeeById("1")).thenReturn(employee);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/profile/employee/{employee_id}/contact-info", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andReturn();
        Employee actual = gson.fromJson(result.getResponse().getContentAsString(), EmployeeResponse.class).getData();
        assertEquals(request.getCellPhone(), actual.getCellPhone());
    }

    @Test
    void test_editEmployment() throws Exception {
        // mock request
        EmploymentRequest request = new EmploymentRequest();
        request.setStartDate(new Date(0));
        request.setEndDate(new Date(1000*3600*24L));
        // set date format
        Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyyy").create();
        String jsonRequest = gson.toJson(request);
        System.out.println(jsonRequest);

        // mock employee
        Employee e = new Employee();
        e.setId("1");
        Mockito.when(service.findEmployeeById("1")).thenReturn(e);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/profile/employee/{employee_id}/employment", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("12/31/1969"));
        assertTrue(result.getResponse().getContentAsString().contains("01/01/1970"));
    }

    @Test
    void test_editEmergencyContact() throws Exception {
        // mock request
        Contact request = new Contact();
        request.setId("1");
        request.setFirstName("Mommy");
        request.setRelationship("Mother");
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);

        // mock employee
        Employee e = new Employee();
        e.setId("1");
        e.setContactList(Arrays.asList(new Contact("1", null, null, null, null, null, null, null, null)));
        Mockito.when(service.findEmployeeById("1")).thenReturn(e);
        e.setContactList(Arrays.asList(request));

        // get result
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/profile/employee/{employee_id}/emergency-contact", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andReturn();
        Employee actual = gson.fromJson(result.getResponse().getContentAsString(), EmployeeResponse.class).getData();
        assertEquals(e.toString(), actual.toString());
    }
}
