//package com.bfteam3.ApplicationService.controller;
//import com.bfteam3.ApplicationService.domain.common.Car;
//import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
//import com.bfteam3.ApplicationService.domain.entity.Application.DigitalDocument;
//import com.bfteam3.ApplicationService.service.ApplicationWorkflowService;
//import com.bfteam3.ApplicationService.service.CarService;
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
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//
//@WebMvcTest(controllers = RemoteCallController.class)
//public class RemoteCallControllerTest {
//    @MockBean
//    private ApplicationWorkflowService applicationService;
//    @MockBean
//    private CarService carService;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void test_getApplicationByEmployeeId() throws Exception{
//        ApplicationWorkFlow appWF = new ApplicationWorkFlow();
//        appWF.setEmployeeId("1");
//        Mockito.when(applicationService.getApplicationByEmployeeId("1")).thenReturn(appWF);
//        ApplicationWorkFlow expected = new ApplicationWorkFlow();
//        expected.setEmployeeId("1");
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/applicationworkflow/{employee_id}", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        ApplicationWorkFlow actual = gson.fromJson(result.getResponse().getContentAsString(), ApplicationWorkFlow.class);
//        assertEquals(expected.toString(), actual.toString());
//    }
//
//    @Test
//    void test_findDigitalDocumentByid() throws Exception {
//        DigitalDocument digitalDocument = new DigitalDocument();
//        digitalDocument.setId((long)1);
//        Mockito.when(applicationService.findDigitalDocumentByid((long)1)).thenReturn(digitalDocument);
//        DigitalDocument expected = new DigitalDocument();
//        expected.setId((long)1);
//        Gson gson = new Gson();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/digitaldocument/{id}", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        DigitalDocument actual = gson.fromJson(result.getResponse().getContentAsString(), DigitalDocument.class);
//        assertEquals(expected.toString(), actual.toString());
//    }
//
//    @Test
//    void test_saveApplicationWorkflow() throws Exception {
//        ApplicationWorkFlow appWF = new ApplicationWorkFlow();
//        Mockito.when(applicationService.saveApplicationWorkflow(Mockito.isA(ApplicationWorkFlow.class))).thenReturn(appWF);
//        Gson gson = new Gson();
//        String expectedJson = gson.toJson(appWF);
//        System.out.println(expectedJson);
//        mockMvc.perform(MockMvcRequestBuilders.post("/applicationworkflow")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(expectedJson))
//                .andExpect(MockMvcResultMatchers.status().isOk()) // status code 200
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json(expectedJson));
//
//    }
//
//    @Test
//    void test_saveDigitalDocument() throws Exception {
//        DigitalDocument dd = new DigitalDocument();
//        Mockito.when(applicationService.saveDigitalDocument(Mockito.isA(DigitalDocument.class))).thenReturn(dd);
//        Gson gson = new Gson();
//        String expectedJson = gson.toJson(dd);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/digitaldocument")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(expectedJson))
//                .andExpect(MockMvcResultMatchers.status().isOk()) // status code 200
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json(expectedJson));
//
//    }
//
//    @Test
//    void test_saveCar() throws Exception {
//        Car car = new Car();
//        Mockito.when(carService.saveCar(Mockito.isA(Car.class))).thenReturn(car);
//        Gson gson = new Gson();
//        String expectedJson = gson.toJson(car);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/car")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(expectedJson))
//                .andExpect(MockMvcResultMatchers.status().isOk()) // status code 200
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json(expectedJson));
//
//    }
//
//    @Test
//    void test_findCarByEmployeeId() throws Exception {
//        List<Car> carList = new ArrayList<>();
//        Car car1 = new Car();
//        Car car2 = new Car();
//        Mockito.when(carService.findCarsByEmployeeId("1")).thenReturn(carList);
//        Gson gson = new Gson();
//
//
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.get("/car/{employee_id}", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        List<Car> actual = gson.fromJson(result.getResponse().getContentAsString(), ArrayList.class);
//        assertEquals(carList.toString(), actual.toString());
//
//    }
//
//}
