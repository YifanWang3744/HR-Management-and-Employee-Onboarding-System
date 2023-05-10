package com.bfteam3.ApplicationService.controller;

import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
import com.bfteam3.ApplicationService.domain.entity.ApplicationStatus;
import com.bfteam3.ApplicationService.domain.response.*;
import com.bfteam3.ApplicationService.domain.response.ResponseStatus;
import com.bfteam3.ApplicationService.service.ApplicationWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * HR reviews application
 */

@RestController
@RequestMapping("/review")
public class ReviewController {
    private ApplicationWorkflowService applicationService;

    @Autowired
    public void setApplicationService(ApplicationWorkflowService applicationService) {
        this.applicationService = applicationService;

    }

    @GetMapping
    @PreAuthorize("hasAuthority('HR')")
    public ApplicationsResponse getApplications(){
        List<ApplicationWorkFlow> applications = applicationService.getApplicationWorkFlows();
        List<ApplicationWorkFlow> pending = applications.stream().filter(a -> a.getApplicationStatus() == ApplicationStatus.PENDING).collect(Collectors.toList());
        List<ApplicationWorkFlow> accepted = applications.stream().filter(a -> a.getApplicationStatus() == ApplicationStatus.ACCEPTED).collect(Collectors.toList());
        List<ApplicationWorkFlow> rejected = applications.stream().filter(a -> a.getApplicationStatus() == ApplicationStatus.REJECTED).collect(Collectors.toList());

        return ApplicationsResponse
                .builder()
                .status(ResponseStatus.builder().success(true).message("all applications fetched").build())
                .accepted(accepted)
                .pending(pending)
                .rejected(rejected)
                .build();
    }

    @PostMapping("/reject/{empId}")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseStatus rejectApplication(@PathVariable String empId, @RequestParam String comment) {
        // set status to rejected, give feedback
        ApplicationWorkFlow app = applicationService.getApplicationByEmployeeId(empId);
        if (app == null) {
            return ResponseStatus
                    .builder()
                    .success(false)
                    .message("Application is not found")
                    .build();
        }
        if (app.getApplicationStatus() != ApplicationStatus.PENDING) {
            return ResponseStatus.builder()
                    .success(false)
                    .message("Can only process Pending application")
                    .build();
        }

        app.setApplicationStatus(ApplicationStatus.REJECTED);
        app.setComment(comment);
        applicationService.saveApplicationWorkflow(app);

        return ResponseStatus.builder()
                .success(true)
                .message("Application rejected")
                .build();
    }

    @GetMapping("/rejected")
    @PreAuthorize("hasAuthority('HR')")
    public ApplicationByStatusResponse viewRejectedApps() {
        List<ApplicationWorkFlow> applications = applicationService.findApplicationByStatus(ApplicationStatus.REJECTED);
        return ApplicationByStatusResponse.builder()
                .status(ResponseStatus.builder().success(true).message("rejected applications fetched").build())
                .applications(applications)
                .build();
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('HR')")
    public ApplicationByStatusResponse viewPendingApps() {
        List<ApplicationWorkFlow> applications = applicationService.findApplicationByStatus(ApplicationStatus.PENDING);
        return ApplicationByStatusResponse.builder()
                .status(ResponseStatus.builder().success(true).message("pending applications fetched").build())
                .applications(applications)
                .build();
    }

    @GetMapping("/accepted")
    @PreAuthorize("hasAuthority('HR')")
    public ApplicationByStatusResponse viewAcceptedApps() {
        List<ApplicationWorkFlow> applications = applicationService.findApplicationByStatus(ApplicationStatus.ACCEPTED);
        return ApplicationByStatusResponse.builder()
                .status(ResponseStatus.builder().success(true).message("accepted applications fetched").build())
                .applications(applications)
                .build();
    }
}

