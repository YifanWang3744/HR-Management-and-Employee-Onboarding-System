//package com.bfteam3.ApplicationService.controller;
//
//import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
//import com.bfteam3.ApplicationService.domain.entity.ApplicationStatus;
//import com.bfteam3.ApplicationService.domain.response.*;
//import com.bfteam3.ApplicationService.service.*;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//@WebMvcTest(controllers = ReviewController.class)
//public class ReviewControllerTest {
//    @MockBean
//    private ApplicationWorkflowService applicationService;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void test_getApplications() throws Exception {
//        Date date = new Date();
//        ApplicationWorkFlow a1 = new ApplicationWorkFlow(1L,"1",date,date,ApplicationStatus.ACCEPTED,"good");
//        ApplicationWorkFlow a2 = new ApplicationWorkFlow(2L,"2",date,date,ApplicationStatus.PENDING,"good");
//        ApplicationWorkFlow a3 = new ApplicationWorkFlow(3L,"3",date,date,ApplicationStatus.REJECTED,"good");
//        List<ApplicationWorkFlow> expected = new ArrayList<>();
//        List<ApplicationWorkFlow> e1 = new ArrayList<>();
//        List<ApplicationWorkFlow> e2 = new ArrayList<>();
//        List<ApplicationWorkFlow> e3 = new ArrayList<>();
//        expected.add(a1);
//        expected.add(a2);
//        expected.add(a3);
//        e1.add(a1);
//        e2.add(a2);
//        e3.add(a3);
//        Mockito.when(applicationService.getApplicationWorkFlows()).thenReturn(expected);
//
//        ApplicationsResponse e = ApplicationsResponse
//                .builder()
//                .status(ResponseStatus.builder().success(true).message("all applications fetched").build())
//                .accepted(e1)
//                .pending(e2)
//                .rejected(e3)
//                .build();
//
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/review")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        ApplicationsResponse actual = gson.fromJson(result.getResponse().getContentAsString(), ApplicationsResponse.class);
//        assertEquals(e.toString(), actual.toString());
//    }
//
//
////    @Test
////    void test_getApplicationByEmployeeId() throws Exception{
////        String empId = "1";
////        Employee employee = new Employee();
////        employee.setId("1");
////        employee.setEmail("test");
////        EmployeeResponse employeeResponse = EmployeeResponse.builder()
////                .status(ResponseStatus.builder().success(true).message("").build())
////                .data(employee).build();
////        Mockito.when(remoteEmployeeService.getProfileByUserId(empId)).thenReturn(employeeResponse);
////        ApplicationResponse expected = ApplicationResponse.builder()
////                .status(ResponseStatus.builder().success(true).message("application retrieved").build())
////                .form(ApplicationForm.builder().email("test").name(new Name())
////                        .profilePic("https://www.nicepng.com/png/detail/933-9332131_profile-picture-default-png.png")
////                        .phoneNumber(new PhoneNumber())
////                        .car(new ArrayList<>()).address(new ArrayList<>())
////                        .referenceAndContact(new ArrayList<>()).visaStatus(new ArrayList<>())
////                        .documents(new ArrayList<>())
////                .build())
////                .build();
////        Gson gson = new Gson();
////        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/review/1")
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andReturn();
////        ApplicationResponse actual = gson.fromJson(result.getResponse().getContentAsString(), ApplicationResponse.class);
////        assertEquals(expected.toString(), actual.toString());
////    }
//
////    @Test
////    void test_rejectApplication() throws Exception{
////        String empId = "1";
////        ApplicationWorkFlow applicationWorkFlow = ApplicationWorkFlow.builder()
////                .id(1L)
////                .applicationStatus(ApplicationStatus.PENDING)
////                .comment("")
////                .createDate(new Date())
////                .lastModificationDate(new Date())
////                .build();
////        Mockito.when(applicationService.getApplicationByEmployeeId(empId)).thenReturn(applicationWorkFlow);
////
////        ResponseStatus expected = ResponseStatus.builder()
////                .success(true)
////                .message("Application status updated to REJECTED")
////                .build();
////
////        StatusRequest statusRequest = StatusRequest.builder()
////                .status("REJECTED").feedback("").build();
////        Gson gson = new Gson();
////        String jsonRequest = gson.toJson(statusRequest);
////        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/review/1")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(jsonRequest))
////                        .andReturn();
////        ResponseStatus actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseStatus.class);
////        assertEquals(expected.toString(), actual.toString());
////    }
//
//    @Test
//    void test_viewRejectedApps() throws Exception{
//        List<ApplicationWorkFlow> lists = new ArrayList<>();
//        ApplicationWorkFlow app = ApplicationWorkFlow.builder()
//                .id(1L)
//                .applicationStatus(ApplicationStatus.REJECTED)
//                .comment("")
//                .createDate(new Date())
//                .lastModificationDate(new Date())
//                .build();
//        lists.add(app);
//        Mockito.when(applicationService.findApplicationByStatus(ApplicationStatus.REJECTED)).thenReturn(lists);
//        ApplicationByStatusResponse expected = ApplicationByStatusResponse.builder()
//                .status(ResponseStatus.builder().success(true).message("rejected applications fetched").build())
//                .applications(lists)
//                .build();
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/review/rejected")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        ApplicationByStatusResponse actual = gson.fromJson(result.getResponse().getContentAsString(), ApplicationByStatusResponse.class);
//        assertEquals(expected.toString(), actual.toString());
//    }
//
//    @Test
//    void test_viewPendingApps() throws Exception{
//        List<ApplicationWorkFlow> lists = new ArrayList<>();
//        ApplicationWorkFlow app = ApplicationWorkFlow.builder()
//                .id(1L)
//                .applicationStatus(ApplicationStatus.PENDING)
//                .comment("")
//                .createDate(new Date())
//                .lastModificationDate(new Date())
//                .build();
//        lists.add(app);
//        Mockito.when(applicationService.findApplicationByStatus(ApplicationStatus.PENDING)).thenReturn(lists);
//        ApplicationByStatusResponse expected = ApplicationByStatusResponse.builder()
//                .status(ResponseStatus.builder().success(true).message("pending applications fetched").build())
//                .applications(lists)
//                .build();
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/review/pending")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        ApplicationByStatusResponse actual = gson.fromJson(result.getResponse().getContentAsString(), ApplicationByStatusResponse.class);
//        assertEquals(expected.toString(), actual.toString());
//    }
//
//    @Test
//    void test_viewAcceptedApps() throws Exception{
//        List<ApplicationWorkFlow> lists = new ArrayList<>();
//        ApplicationWorkFlow app = ApplicationWorkFlow.builder()
//                .id(1L)
//                .applicationStatus(ApplicationStatus.ACCEPTED)
//                .comment("")
//                .createDate(new Date())
//                .lastModificationDate(new Date())
//                .build();
//        lists.add(app);
//        Mockito.when(applicationService.findApplicationByStatus(ApplicationStatus.ACCEPTED)).thenReturn(lists);
//        ApplicationByStatusResponse expected = ApplicationByStatusResponse.builder()
//                .status(ResponseStatus.builder().success(true).message("accepted applications fetched").build())
//                .applications(lists)
//                .build();
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/review/accepted")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        ApplicationByStatusResponse actual = gson.fromJson(result.getResponse().getContentAsString(), ApplicationByStatusResponse.class);
//        assertEquals(expected.toString(), actual.toString());
//    }
//
//}