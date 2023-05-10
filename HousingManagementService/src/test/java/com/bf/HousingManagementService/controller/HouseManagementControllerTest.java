package com.bf.HousingManagementService.controller;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.domain.response.AllCarsResponse;
import com.bf.HousingManagementService.domain.response.EmployeesResponse;
import com.bf.HousingManagementService.domain.response.HouseDetailResponse;
import com.bf.HousingManagementService.domain.response.HousingDetailsResponse;
import com.bf.HousingManagementService.domain.resultWrapper.EmployeeWrapper;
import com.bf.HousingManagementService.domain.resultWrapper.HouseDetail;
import com.bf.HousingManagementService.entity.Car;
import com.bf.HousingManagementService.entity.Employee;
import com.bf.HousingManagementService.entity.Landlord;
import com.bf.HousingManagementService.domain.response.HouseAssignmentResponse;
import com.bf.HousingManagementService.domain.response.HouseResponse;
import com.bf.HousingManagementService.domain.response.HouseSummariesResponse;
import com.bf.HousingManagementService.domain.response.OccupantResponse;
import com.bf.HousingManagementService.domain.resultWrapper.HouseSummary;
import com.bf.HousingManagementService.entity.Employee;
import com.bf.HousingManagementService.exception.EmployeeNotFoundException;
import com.bf.HousingManagementService.exception.NoAvailableHouseException;
import com.bf.HousingManagementService.service.RemoteApplicationService;
import com.bf.HousingManagementService.service.RemoteEmployeeService;
import com.bf.HousingManagementService.service.RemoteHousingService;
import com.google.gson.Gson;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(HouseManagementController.class)
public class HouseManagementControllerTest {
    @MockBean
    private RemoteHousingService housingService;

    @MockBean
    private RemoteEmployeeService employeeService;

    @MockBean
    private RemoteApplicationService applicationService;

    @Autowired
    private MockMvc mockMvc;

    private Gson gson = new Gson();

    @Test
    void test_assign() throws Exception {
        HouseAssignmentResponse house = HouseAssignmentResponse.builder()
                        .houseId(1L)
                        .build();
        Mockito.when(housingService.assign("1")).thenReturn(house);
        Mockito.when(employeeService.updateEmployeeHouseInfo("", 1L,"")).thenReturn(ResponseStatus.builder().build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hr/house/new/{empId}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        HouseAssignmentResponse actual = gson.fromJson(result.getResponse().getContentAsString(), HouseAssignmentResponse.class);
        assertEquals(1L, actual.getHouseId());
    }

    @Test
    void test_viewHouse() throws Exception {
        List<HouseSummary> houseSummaries = new ArrayList<>(Arrays.asList(
                HouseSummary.builder()
                        .houseId(1L)
                        .houseAddress("Address 1")
                        .occupantsNum(1)
                        .build(),
                HouseSummary.builder()
                        .houseId(2L)
                        .houseAddress("Address 2")
                        .occupantsNum(2)
                        .build()
        ));

        Mockito.when(housingService.viewHouses("")).thenReturn(HouseSummariesResponse.builder().data(houseSummaries).build());
        Mockito.when(employeeService.getOccupantsByHouseId(1L,"")).thenReturn(OccupantResponse.builder().status(ResponseStatus.builder().success(true).build()).num(1).build());
        Mockito.when(employeeService.getOccupantsByHouseId(2L,"")).thenReturn(OccupantResponse.builder().status(ResponseStatus.builder().success(true).build()).num(2).build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hr/houses")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<HouseSummary> actual = gson.fromJson(result.getResponse().getContentAsString(), HouseSummariesResponse.class).getData();
        assertEquals(houseSummaries.toString(), actual.toString());
    }

    @Test
    void test_viewHouseByIdSuccess() throws Exception {
        List<EmployeeWrapper> occupants = new ArrayList<>();
        occupants.add(
                EmployeeWrapper.builder()
                        .id("1")
                        .name("Jack Wang")
                        .build()
        );
        occupants.add(
                EmployeeWrapper.builder()
                        .id("2")
                        .name("Tom Huang")
                        .build()
        );
        HouseDetailResponse houseDetailResponse = HouseDetailResponse.builder()
                .data(
                        HouseDetail.builder()
                                .houseAddress("87th St")
                                .landlord(
                                        Landlord.builder()
                                                .id(1L)
                                                .firstName("Jack")
                                                .lastName("Wang")
                                                .build()
                                )
                                .build()
                )
                .build();

        EmployeesResponse employeesResponse = EmployeesResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("get occupant details by houseId")
                                .build()
                )
                .data(
                        Arrays.asList(
                                Employee.builder()
                                        .id("1")
                                        .firstName("Jack")
                                        .lastName("Wang")
                                        .houseId(1L)
                                        .build(),
                                Employee.builder()
                                        .id("2")
                                        .firstName("Tom")
                                        .lastName("Huang")
                                        .houseId(1L)
                                        .build())
                )
                .build();

        AllCarsResponse allCarsResponse = AllCarsResponse.builder()
                .cars(new ArrayList<>())
                .build();

        Mockito.when(housingService.viewHouseById(1L, 0, 3,"")).thenReturn(houseDetailResponse);
        Mockito.when(employeeService.getOccupantDetailsByHouseId(1L,"")).thenReturn(employeesResponse);
        Mockito.when(applicationService.findCarsByEmployeeId("1","")).thenReturn((List<Car>) allCarsResponse);
        Mockito.when(applicationService.findCarsByEmployeeId("2", "")).thenReturn((List<Car>) allCarsResponse);

        HouseDetailResponse expected = HouseDetailResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(true)
                                .message("House detail fetched")
                                .build()
                )
                .data(
                        HouseDetail.builder()
                                .houseAddress("87th St")
                                .landlord(
                                        Landlord.builder()
                                                .id(1L)
                                                .firstName("Jack")
                                                .lastName("Wang")
                                                .build()
                                )
                                .occupants(occupants)
                                .build()
                )
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hr/house/{houseId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Gson gson = new Gson();
        HouseDetailResponse actual = gson.fromJson(result.getResponse().getContentAsString(), HouseDetailResponse.class);

        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    void test_viewHouseByIdFail() throws Exception {
        HouseDetailResponse houseDetailResponse = HouseDetailResponse.builder()
                .data(
                        HouseDetail.builder()
                                .houseAddress("87th St")
                                .landlord(
                                        Landlord.builder()
                                                .id(1L)
                                                .firstName("Jack")
                                                .lastName("Wang")
                                                .build()
                                )
                                .build()
                )
                .build();

        EmployeesResponse employeesResponse = EmployeesResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(false)
                                .build()
                )
                .build();


        Mockito.when(housingService.viewHouseById(1L, 0, 3, "")).thenReturn(houseDetailResponse);
        Mockito.when(employeeService.getOccupantDetailsByHouseId(1L, "")).thenReturn(employeesResponse);

        HouseDetailResponse expected = HouseDetailResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(false)
                                .message("House detail not fetched")
                                .build()
                )
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hr/house/{houseId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Gson gson = new Gson();
        HouseDetailResponse actual = gson.fromJson(result.getResponse().getContentAsString(), HouseDetailResponse.class);

        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    void test_deleteHouse() throws Exception {
        ResponseStatus expected = ResponseStatus.builder()
                .success(true)
                .message("house deleted")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/hr/house/{houseId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Gson gson = new Gson();
        ResponseStatus actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseStatus.class);

        assertEquals(actual.toString(), expected.toString());
    }
}
