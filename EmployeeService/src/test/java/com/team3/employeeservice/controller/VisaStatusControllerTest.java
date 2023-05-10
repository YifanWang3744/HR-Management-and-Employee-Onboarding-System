package com.team3.employeeservice.controller;

import com.google.gson.Gson;
import com.team3.employeeservice.domain.Employee;
import com.team3.employeeservice.domain.PersonalDocument;
import com.team3.employeeservice.domain.VisaStatus;
import com.team3.employeeservice.request.PersonalDocumentRequest;
import com.team3.employeeservice.request.VisaStatusRequest;
import com.team3.employeeservice.response.PersonalDocumentListResponse;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = VisaStatusController.class)
public class VisaStatusControllerTest {

    @MockBean
    private EmployeeService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_addVisaStatus_success() throws Exception {
        VisaStatusRequest visaStatusRequest = VisaStatusRequest.builder()
                .visaType("OPT")
                .startDate("03/01/2023")
                .endDate("03/01/2024")
                .build();

        List<VisaStatus> visaStatusList = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis());
        visaStatusList.add(VisaStatus.builder()
                .visaType("OPT")
                .activeFlag(true)
                .startDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2023"))
                .endDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2024"))
                .lastModificationDate(currentDate)
                .build());

        Employee employee = new Employee("1", 1L, "Jack", "Wang", null,
                null, null, "jack@gmail.com", "1111111111",
                null, "Male", "111111111", null,
                null, null, null, null, null,
                null, null, null, null);

        Mockito.when(service.findEmployeeById("1")).thenReturn(employee);

        VisaStatusListResponse expected = VisaStatusListResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Visa status added.")
                        .build())
                .visaStatusList(visaStatusList)
                .build();

        Gson gson = new Gson();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/visa-status/{employee_id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(visaStatusRequest)))
                .andReturn();
        VisaStatusListResponse actual = gson.fromJson(result.getResponse().getContentAsString(), VisaStatusListResponse.class);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void test_addVisaStatus_fail() throws Exception {
        VisaStatusRequest visaStatusRequest = VisaStatusRequest.builder()
                .visaType("OPT")
                .build();

        VisaStatusListResponse expected = VisaStatusListResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(false)
                                .message("Validation error")
                                .build()
                )
                .build();

        Gson gson = new Gson();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/visa-status/{employee_id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(visaStatusRequest)))
                .andReturn();
        VisaStatusListResponse actual = gson.fromJson(result.getResponse().getContentAsString(), VisaStatusListResponse.class);

        assertEquals(expected.toString(), actual.toString());
    }


}
