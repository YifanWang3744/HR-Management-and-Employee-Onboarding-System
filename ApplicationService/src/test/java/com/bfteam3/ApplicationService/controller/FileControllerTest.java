//package com.bfteam3.ApplicatonMicroService.controller;
//
//import com.bfteam3.ApplicatonMicroService.domain.entity.Application.ApplicationWorkFlow;
//import com.bfteam3.ApplicatonMicroService.domain.entity.ApplicationStatus;
//import com.bfteam3.ApplicatonMicroService.domain.response.ResponseStatus;
//import com.bfteam3.ApplicatonMicroService.service.ApplicationWorkflowService;
//import com.bfteam3.ApplicatonMicroService.service.DigitalDocumentService;
//import com.bfteam3.ApplicatonMicroService.service.FileService;
//import com.bfteam3.ApplicatonMicroService.service.RemoteEmployeeService;
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
//@WebMvcTest(controllers = FileController.class, properties = {"security.basic.enabled=false"})
//@AutoConfigureMockMvc(addFilters = false)
////@EnableAutoConfiguration(exclude = SecurityConfig.class)
//@EnableAutoConfiguration
//public class FileControllerTest {
//
//    @MockBean
//    private FileService fileService;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void test_downloadFile() throws Exception{
//        String fileName = "a";
//        byte[] data = "Hello".getBytes();
//        ByteArrayResource resource = new ByteArrayResource(data);
//        Mockito.when(fileService.downloadFile(fileName)).thenReturn(data);
//        ResponseEntity expected = new ResponseEntity<>(ResponseEntity.ok().contentLength(data.length)
//                .header("Content-type", "application/octet-stream")
//                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
//                .body(resource).getStatusCode());
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/storage/download/a")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
////        System.out.println(result.getResponse().getContentAsString());
////        ResponseEntity actual = gson.fromJson(result.getResponse().getContentAsString(), ResponseEntity.class);
////        assertEquals(expected.getBody().toString(), actual.getBody().toString());
//        assertEquals(true,true);
//    }
//
//}
