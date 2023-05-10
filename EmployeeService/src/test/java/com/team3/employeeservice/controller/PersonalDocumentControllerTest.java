package com.team3.employeeservice.controller;

import com.google.gson.Gson;
import com.team3.employeeservice.domain.Employee;
import com.team3.employeeservice.domain.PersonalDocument;
import com.team3.employeeservice.request.PersonalDocumentRequest;
import com.team3.employeeservice.response.PersonalDocumentListResponse;
import com.team3.employeeservice.response.ResponseStatus;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = PersonalDocumentController.class)
public class PersonalDocumentControllerTest {

    @MockBean
    private EmployeeService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_addPersonalDocument_success() throws Exception {
        PersonalDocumentRequest personalDocumentRequest = PersonalDocumentRequest.builder()
                .id("1")
                .path("this is a path")
                .title("this is a title")
                .comment("this is a comment")
                .build();
        List<PersonalDocument> personalDocumentList = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis());
        personalDocumentList.add(PersonalDocument.builder()
                .id("1")
                .path("this is a path")
                .title("this is a title")
                .comment("this is a comment")
                .createDate(currentDate)
                .build());

        Employee employee = new Employee("1", 1L, "Jack", "Wang", null,
                null, null, "jack@gmail.com", "1111111111",
                null, "Male", "111111111", null,
                null, null, null, null, null,
                null, null, null, null);

        Mockito.when(service.findEmployeeById("1")).thenReturn(employee);

        PersonalDocumentListResponse expected = PersonalDocumentListResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Personal document added.")
                        .build())
                .personalDocumentList(personalDocumentList)
                .build();

        Gson gson = new Gson();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/personal-document/{employee_id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(personalDocumentRequest)))
                .andReturn();
        PersonalDocumentListResponse actual = gson.fromJson(result.getResponse().getContentAsString(), PersonalDocumentListResponse.class);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void test_addPersonalDocument_fail() throws Exception {
        PersonalDocumentRequest personalDocumentRequest = PersonalDocumentRequest.builder()
                .id("1")
                .build();

        PersonalDocumentListResponse expected = PersonalDocumentListResponse.builder()
                .status(
                        ResponseStatus.builder()
                                .success(false)
                                .message("Validation error")
                                .build()
                )
                .build();

        Gson gson = new Gson();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/personal-document/{employee_id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(personalDocumentRequest)))
                .andReturn();
        PersonalDocumentListResponse actual = gson.fromJson(result.getResponse().getContentAsString(), PersonalDocumentListResponse.class);

        assertEquals(expected.toString(), actual.toString());
    }
}
