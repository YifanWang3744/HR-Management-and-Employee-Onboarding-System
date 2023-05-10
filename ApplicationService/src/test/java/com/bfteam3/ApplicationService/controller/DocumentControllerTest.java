//package com.bfteam3.ApplicationService.controller;
//
//import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
//import com.bfteam3.ApplicationService.domain.entity.ApplicationStatus;
//import com.bfteam3.ApplicationService.domain.response.ResponseStatus;
//import com.bfteam3.ApplicationService.service.ApplicationWorkflowService;
//import com.bfteam3.ApplicationService.service.FileService;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@WebMvcTest(controllers = DocumentController.class, properties = {"security.basic.enabled=false"})
//@AutoConfigureMockMvc(addFilters = false)
////@EnableAutoConfiguration(exclude = SecurityConfig.class)
//@EnableAutoConfiguration
//public class DocumentControllerTest {
//
//    @MockBean
//    private FileService fileService;
//    @MockBean
//    private ApplicationWorkflowService applicationService;
//    @Autowired
//    private MockMvc mockMvc;
//
////    @MockBean
////    private JwtProvider jwtProvider;
//
//    @Test
//    void test_downloadFile() throws Exception {
//        String fileName = "a";
//        byte[] data = "Hello".getBytes();
//        ByteArrayResource resource = new ByteArrayResource(data);
//        Mockito.when(fileService.downloadFile(fileName)).thenReturn(data);
//        ResponseEntity expected = new ResponseEntity<>(ResponseEntity.ok().contentLength(data.length)
//        .header("Content-type", "application/octet-stream")
//                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
//                .body(resource).getStatusCode());
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/docu/download/a")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        int actual = result.getResponse().getStatus();
//        assertEquals(expected.getStatusCode().value(), actual);
//    }
//
//    @Test
//    void test_deleteFile() throws Exception {
//        String fileName = "a";
//        String response = fileName + " removed ...";
//        Mockito.when(fileService.deleteFile(fileName)).thenReturn(response);
//        ResponseStatus expected = ResponseStatus.builder().success(true).message("Delete successfully").build();
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/docu/delete", "a"))
//                .andReturn();
//
////        ResponseEntity actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseEntity.class);
////        result.getResponse().getStatus();
//        assertEquals(true, true);
//    }
//
//    @Test
//    void test_submit() throws Exception{
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        app.setEmployeeId("1");
//        Date date = new Date();
//        app.setCreateDate(date);
//        app.setLastModificationDate(date);
//        app.setApplicationStatus(ApplicationStatus.PENDING);
//        Mockito.when(applicationService.saveApplicationWorkflow(app)).thenReturn(null);
//        ResponseStatus expected = ResponseStatus.builder().message("Please wait for HR to review your application")
//                .success(true).build();
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/docu/submit/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        ResponseStatus actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseStatus.class);
//        assertEquals(expected.toString(), actual.toString());
//    }
//
//
//
////
////    @Test
////    void test_submit() throws Exception{
////        ApplicationWorkFlow app = new ApplicationWorkFlow();
////        app.setEmployeeId("1");
////        Date date = new Date();
////        app.setCreateDate(date);
////        app.setLastModificationDate(date);
////        app.setApplicationStatus(ApplicationStatus.PENDING);
////        Mockito.when(applicationService.saveApplicationWorkflow(app)).thenReturn(null);
////        ResponseStatus expected = ResponseStatus.builder().message("Please wait for HR to review your application")
////                .success(true).build();
////        Gson gson = new Gson();
////        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/docu/submit/1")
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andReturn();
////        ResponseStatus actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseStatus.class);
////        assertEquals(expected.toString(), actual.toString());
////    }
//
//}
