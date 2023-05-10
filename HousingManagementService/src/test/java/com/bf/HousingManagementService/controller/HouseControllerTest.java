package com.bf.HousingManagementService.controller;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.domain.response.EmployeeResponse;
import com.bf.HousingManagementService.domain.response.EmployeesResponse;
import com.bf.HousingManagementService.domain.response.HouseResponse;
import com.bf.HousingManagementService.domain.response.HousingDetailsResponse;
import com.bf.HousingManagementService.entity.Employee;
import com.bf.HousingManagementService.entity.House;
import com.bf.HousingManagementService.entity.Landlord;
import com.bf.HousingManagementService.entity.Roommate;
import com.bf.HousingManagementService.exception.EmployeeNotFoundException;
import com.bf.HousingManagementService.service.RemoteEmployeeService;
import com.bf.HousingManagementService.service.RemoteHousingService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(controllers = HouseController.class)
public class HouseControllerTest {

    @MockBean
    private RemoteHousingService remoteHouseService;

    @MockBean
    private RemoteEmployeeService remoteEmployeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_getHouseByIdSuccess() throws Exception {
        House house = House.builder()
                .id(1L)
                .landlord(
                        Landlord.builder()
                                .id(1L)
                                .firstName("Jack")
                                .lastName("Wang")
                                .build()
                )
                .address("45th St")
                .build();

        HouseResponse houseResponse = HouseResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .success(true)
                                .message("get house by id")
                                .build()
                )
                .house(house)
                .build();

        Employee employee = Employee.builder()
                .id("1")
                .firstName("Emma")
                .lastName("Waston")
                .houseId(1L)
                .build();

        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("get employee by id")
                                .build()
                )
                .data(employee)
                .build();

        List<Employee> employees = new ArrayList();

        employees.add(
                Employee.builder()
                        .id("1")
                        .firstName("Emma")
                        .lastName("Waston")
                        .houseId(1L)
                        .build()
        );

        employees.add(
                Employee.builder()
                        .id("2")
                        .firstName("Tom")
                        .lastName("Huang")
                        .houseId(1L)
                        .build()
        );

        EmployeesResponse employeesResponse = EmployeesResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("get all employees")
                                .build()
                )
                .data(employees)
                .build();

        List<Roommate> roommateList = new ArrayList<>();
        for (Employee e : employees) {
            roommateList.add(Roommate.builder()
                    .firstName(e.getFirstName())
                    .lastName(e.getLastName())
                    .preferredName(e.getPreferredName())
                    .phoneNumber(e.getCellPhone())
                    .build());
        }

        Mockito.when(remoteHouseService.getHouseById(1L,"")).thenReturn(houseResponse);
        Mockito.when(remoteEmployeeService.getProfileByUserId("1")).thenReturn(employeeResponse);
        Mockito.when(remoteEmployeeService.getAllProfiles("")).thenReturn(employeesResponse);

        HousingDetailsResponse expected = HousingDetailsResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("Get housing details")
                                .build()
                )
                .address("45th St")
                .roommateList(roommateList)
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/employee/{employeeId}/house/{houseId}", "1", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Gson gson = new Gson();
        HousingDetailsResponse actual = gson.fromJson(result.getResponse().getContentAsString(), HousingDetailsResponse.class);

        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    void test_getHouseByIdFailHouseNotFound() throws Exception {
        HouseResponse houseResponse = HouseResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .success(false)
                                .message("house not found")
                                .build()
                )
                .house(null)
                .build();

        Mockito.when(remoteHouseService.getHouseById(1L,"")).thenReturn(houseResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/employee/{employeeId}/house/{houseId}", "1", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Gson gson = new Gson();
        HousingDetailsResponse actual = gson.fromJson(result.getResponse().getContentAsString(), HousingDetailsResponse.class);

        HousingDetailsResponse expected = HousingDetailsResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(false)
                                .message("house not found")
                                .build()
                ).build();

        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    void test_getHouseByIdFailAccessDenied() throws Exception {
        House house = House.builder()
                .id(1L)
                .landlord(
                        Landlord.builder()
                                .id(1L)
                                .firstName("Jack")
                                .lastName("Wang")
                                .build()
                )
                .address("45th St")
                .build();

        HouseResponse houseResponse = HouseResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .success(true)
                                .message("get house by id")
                                .build()
                )
                .house(house)
                .build();

        Employee employee = Employee.builder()
                .id("1")
                .firstName("Emma")
                .lastName("Waston")
                .houseId(2L)
                .build();

        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("get employee by id")
                                .build()
                )
                .data(employee)
                .build();

        Mockito.when(remoteHouseService.getHouseById(1L,"")).thenReturn(houseResponse);
        Mockito.when(remoteEmployeeService.getProfileByUserId("1")).thenReturn(employeeResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/employee/{employeeId}/house/{houseId}", "1", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Gson gson = new Gson();
        HousingDetailsResponse actual = gson.fromJson(result.getResponse().getContentAsString(), HousingDetailsResponse.class);

        HousingDetailsResponse expected = HousingDetailsResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(false)
                                .message("access denied")
                                .build()
                ).build();

        assertEquals(actual.toString(), expected.toString());
    }
}
