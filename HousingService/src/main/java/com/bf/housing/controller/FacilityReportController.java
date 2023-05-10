package com.bf.housing.controller;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.domain.request.CommentRequest;
import com.bf.housing.domain.request.EditCommentRequest;
import com.bf.housing.domain.request.FacilityReportRequest;
import com.bf.housing.domain.response.AllFacilityReportResponse;
import com.bf.housing.domain.response.AllReportDetailResponse;
import com.bf.housing.entity.FacilityReport;
import com.bf.housing.entity.FacilityReportDetail;
import com.bf.housing.exception.AccessDeniedException;
import com.bf.housing.exception.FacilityReportNotFoundException;
import com.bf.housing.service.FacilityReportService;
import com.bf.housing.service.ReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/facility-report")
public class FacilityReportController {

    private FacilityReportService service;
    private ReportDetailService reportDetailService;

    @Autowired
    public FacilityReportController(FacilityReportService service) {
        this.service = service;
    }

    @Autowired
    public void setReportDetailService(ReportDetailService reportDetailService) {
        this.reportDetailService = reportDetailService;
    }

    //    @GetMapping("/employees")
//    public List<FacilityReportResponse> getAllFacilityReports() {
//        List<FacilityReport> reports = service.findAllReports();
//        return service.getAllFacilityReportResponses(reports);
//    }

    @GetMapping("")
    public AllFacilityReportResponse getFacilityReportsByEmployee() {
        String employee_id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<FacilityReport> reports = service.findReportsByEmployee(employee_id);

        return AllFacilityReportResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Get all facility reports of current employee").build())
                .data(service.getAllReportsResponses(reports))
                .build();
    }

    @GetMapping("/hr/{report_id}")
    @PreAuthorize("hasAuthority('HR')")
    public AllReportDetailResponse getFacilityReportDetails(@PathVariable Long report_id) {
//        List<FacilityReport> reports = service.findReportsByEmployee(employee_id);
//        FacilityReport report = reports.stream().filter(r -> r.getId() == report_id).findFirst().get();
//        List<FacilityReportDetail> comments = report.getComments();

        List<FacilityReportDetail> comments = reportDetailService.findAllByReportId(report_id);
        return AllReportDetailResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Get current report details").build())
                .comments(comments)
                .build();
    }

    @PostMapping("")
    public AllFacilityReportResponse createFacilityReport(@RequestBody FacilityReportRequest request) {
        String employee_id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FacilityReport report = service.createFacilityReport(employee_id, request);

        return AllFacilityReportResponse.builder()
                .status(ResponseStatus.builder().success(true).message("New facility report created").build())
                .data(service.getAllReportsResponses(Arrays.asList(report)))
                .build();
    }

    @PostMapping("/{report_id}")
    @PreAuthorize("permitAll()")
    public AllFacilityReportResponse addComment(@PathVariable Long report_id,
                                      @RequestBody CommentRequest request) throws FacilityReportNotFoundException, AccessDeniedException {
        String employee_id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FacilityReport report = service.addComment(report_id, employee_id, request);

        return AllFacilityReportResponse.builder()
                .status(ResponseStatus.builder().success(true).message("New comment added").build())
                .data(service.getAllReportsResponses(Arrays.asList(report)))
                .build();
    }

    @PatchMapping("/edit-comment")
    @PreAuthorize("permitAll()")
    public AllFacilityReportResponse editComment(@RequestBody EditCommentRequest request) throws AccessDeniedException {
        String employee_id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FacilityReport report = service.updateComment(employee_id, request);

        return AllFacilityReportResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Comment id=" + request.getId() + " edited").build())
                .data(service.getAllReportsResponses(Arrays.asList(report)))
                .build();
    }

    @PostMapping("/hr/{report_id}")
    @PreAuthorize("hasAuthority('HR')")
    public AllFacilityReportResponse addHRComment(@PathVariable Long report_id,
                                                @RequestBody CommentRequest request) throws FacilityReportNotFoundException, AccessDeniedException {
        String employee_id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FacilityReport report = service.addHRComment(report_id, employee_id, request);

        return AllFacilityReportResponse.builder()
                .status(ResponseStatus.builder().success(true).message("New comment added").build())
                .data(service.getAllReportsResponses(Arrays.asList(report)))
                .build();
    }

    @PatchMapping("/hr/edit-comment")
    @PreAuthorize("hasAuthority('HR')")
    public AllFacilityReportResponse editHRComment(@RequestBody EditCommentRequest request) throws AccessDeniedException {
        String employee_id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FacilityReport report = service.updateComment(employee_id, request);

        return AllFacilityReportResponse.builder()
                .status(ResponseStatus.builder().success(true).message("Comment id=" + request.getId() + " edited").build())
                .data(service.getAllReportsResponses(Arrays.asList(report)))
                .build();
    }
}
