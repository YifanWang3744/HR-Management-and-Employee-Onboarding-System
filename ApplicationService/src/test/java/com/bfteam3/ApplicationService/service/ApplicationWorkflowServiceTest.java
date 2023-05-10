//package com.bfteam3.ApplicationService.service;
//import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
//import com.bfteam3.ApplicationService.domain.entity.Application.DigitalDocument;
//import com.bfteam3.ApplicationService.domain.entity.ApplicationStatus;
//import com.bfteam3.ApplicationService.repository.Application.ApplicationWorkFlowRepo;
//import com.bfteam3.ApplicationService.repository.Application.DigitalDocumentRepo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ApplicationWorkflowServiceTest {
//
//    @Mock
//    private ApplicationWorkFlowRepo applicationWorkFlowRepo;
//
//    @Mock
//    private DigitalDocumentRepo digitalDocumentRepo;
//
//    @InjectMocks
//    private ApplicationWorkflowService applicationWorkflowService;
//
//    @Test
//    void test_saveDigitalDocument_success(){
//        DigitalDocument dd = new DigitalDocument();
//        Mockito.when(digitalDocumentRepo.save(dd)).thenReturn(dd);
//        assertEquals(dd, applicationWorkflowService.saveDigitalDocument(dd));
//    }
//
//    @Test
//    void test_saveDigitalDocument_fail(){
//        DigitalDocument dd = new DigitalDocument();
//        Mockito.when(digitalDocumentRepo.save(dd)).thenReturn(dd);
//        assertNotEquals(new DigitalDocument(), applicationWorkflowService.saveDigitalDocument(dd));
//    }
//
//    @Test
//    void test_saveApplicationWorkflow_success(){
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        Mockito.when(applicationWorkFlowRepo.save(app)).thenReturn(app);
//        assertEquals(app, applicationWorkflowService.saveApplicationWorkflow(app));
//    }
//
//    @Test
//    void test_saveApplicationWorkflow_fail(){
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        Mockito.when(applicationWorkFlowRepo.save(app)).thenReturn(app);
//        assertNotEquals(new ApplicationWorkFlow(), applicationWorkflowService.saveApplicationWorkflow(app));
//    }
//
//    @Test
//    void test_getApplicationByEmployeeId_success(){
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        Mockito.when(applicationWorkFlowRepo.findApplicationByEmployeeId("oeigoieg")).thenReturn(app);
//        assertEquals(app, applicationWorkflowService.getApplicationByEmployeeId("oeigoieg"));
//    }
//
//    @Test
//    void test_getApplicationByEmployeeId_fail(){
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        Mockito.when(applicationWorkFlowRepo.findApplicationByEmployeeId("oeigoieg")).thenReturn(app);
//        assertNotEquals(new ApplicationWorkFlow(), applicationWorkflowService.getApplicationByEmployeeId("oeigoieg"));
//    }
//
//
//    @Test
//    void test_getApplicationWorkFlows_success(){
//        List<ApplicationWorkFlow> appList = new ArrayList<>();
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        appList.add(app);
//        Mockito.when(applicationWorkFlowRepo.findAll()).thenReturn(appList);
//        assertEquals(appList, applicationWorkflowService.getApplicationWorkFlows());
//    }
//
//    @Test
//    void test_getApplicationWorkFlows_fail(){
//        List<ApplicationWorkFlow> appList = new ArrayList<>();
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        appList.add(app);
//        Mockito.when(applicationWorkFlowRepo.findAll()).thenReturn(appList);
//        assertNotEquals(null, applicationWorkflowService.getApplicationWorkFlows());
//    }
//
//
//    @Test
//    void test_findApplicationByStatus_success(){
//        List<ApplicationWorkFlow> appList = new ArrayList<>();
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        app.setApplicationStatus(ApplicationStatus.PENDING);
//        appList.add(app);
//        Mockito.when(applicationWorkFlowRepo.findAllByApplicationStatus(ApplicationStatus.PENDING)).thenReturn(appList);
//        assertEquals(appList, applicationWorkflowService.findApplicationByStatus(ApplicationStatus.PENDING));
//    }
//
//    @Test
//    void test_findApplicationByStatus_fail(){
//        List<ApplicationWorkFlow> appList = new ArrayList<>();
//        ApplicationWorkFlow app = new ApplicationWorkFlow();
//        app.setApplicationStatus(ApplicationStatus.PENDING);
//        appList.add(app);
//         ApplicationWorkFlow app2 = new ApplicationWorkFlow();
//         app2.setApplicationStatus(ApplicationStatus.REJECTED);
//         List<ApplicationWorkFlow> appList2 = new ArrayList<>();
//         appList2.add(app2);
//        Mockito.when(applicationWorkFlowRepo.findAllByApplicationStatus(ApplicationStatus.PENDING)).thenReturn(appList);
//        assertNotEquals(appList2, applicationWorkflowService.findApplicationByStatus(ApplicationStatus.PENDING));
//    }
//}
