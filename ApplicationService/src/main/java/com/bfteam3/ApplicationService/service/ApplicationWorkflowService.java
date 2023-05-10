package com.bfteam3.ApplicationService.service;
import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
import com.bfteam3.ApplicationService.domain.entity.Application.DigitalDocument;
import com.bfteam3.ApplicationService.domain.entity.ApplicationStatus;
import com.bfteam3.ApplicationService.repository.Application.ApplicationWorkFlowRepo;
import com.bfteam3.ApplicationService.repository.Application.DigitalDocumentRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

@Service
public class ApplicationWorkflowService {

    private final ApplicationWorkFlowRepo applicationWorkFlowRepo;
    private final DigitalDocumentRepo digitalDocumentRepo;
    private final CarService carService;
    private final FileService fileService;

    public ApplicationWorkflowService(ApplicationWorkFlowRepo applicationWorkFlowRepo, DigitalDocumentRepo digitalDocumentRepo, CarService carService, FileService fileService) {
        this.applicationWorkFlowRepo = applicationWorkFlowRepo;
        this.digitalDocumentRepo = digitalDocumentRepo;
        this.carService = carService;
        this.fileService = fileService;
    }

    public ApplicationWorkFlow getApplicationByEmployeeId(String id){
        return applicationWorkFlowRepo.findApplicationByEmployeeId(id);
    }

    public DigitalDocument saveDigitalDocument(DigitalDocument doc){
        return digitalDocumentRepo.save(doc);
    }

    public ApplicationWorkFlow saveApplicationWorkflow(ApplicationWorkFlow submission){
        return applicationWorkFlowRepo.save(submission);
    }

    public DigitalDocument findDigitalDocumentByid(Long id) {
        Optional<DigitalDocument> res = digitalDocumentRepo.findById(id);
        return (res.isPresent() ? res.get() : null);
    }

    public List<ApplicationWorkFlow> getApplicationWorkFlows(){
         return applicationWorkFlowRepo.findAll();
    }

    public List<ApplicationWorkFlow> findApplicationByStatus(ApplicationStatus status) {
        return applicationWorkFlowRepo.findAllByApplicationStatus(status);
    }

}
