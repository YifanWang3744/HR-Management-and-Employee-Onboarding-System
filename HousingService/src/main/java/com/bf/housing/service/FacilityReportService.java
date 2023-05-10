package com.bf.housing.service;

import com.bf.housing.domain.request.CommentRequest;
import com.bf.housing.domain.request.EditCommentRequest;
import com.bf.housing.domain.request.FacilityReportRequest;
import com.bf.housing.domain.response.CommentResponse;
import com.bf.housing.domain.response.ReportsResponse;
import com.bf.housing.entity.FacilityReport;
import com.bf.housing.entity.FacilityReportDetail;
import com.bf.housing.entity.ReportStatus;
import com.bf.housing.exception.AccessDeniedException;
import com.bf.housing.exception.FacilityReportNotFoundException;
import com.bf.housing.repository.FacilityReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacilityReportService {
    private FacilityReportRepository repository;

    @Autowired
    public void setRepository(FacilityReportRepository repository) {
        this.repository = repository;
    }

    public List<FacilityReport> findAllReports() {
        return repository.findAll();
    }

    public FacilityReport findReportById(Long report_id) throws FacilityReportNotFoundException {
        return repository.findById(report_id).orElseThrow(() -> new FacilityReportNotFoundException("No such facility report."));
    }

    public List<FacilityReport> findReportsByEmployee(String employeeId) {
        return repository.findAllByEmployeeId(employeeId);
    }

    public List<ReportsResponse> getAllReportsResponses(List<FacilityReport> reports) {

        return reports.stream()
                .map(report -> {
                    List<FacilityReportDetail> comments = report.getComments();
                    List<CommentResponse> commentResponseList = new ArrayList<>();
                    if (!comments.isEmpty()) {
                        for (FacilityReportDetail comment : comments) {
                            commentResponseList.add(
                                    CommentResponse.builder()
                                            .description(comment.getComment())
                                            .author(comment.getEmployeeId())
                                            .lastModifiedTime(Optional.ofNullable(
                                                            comment.getLastModificationDate())
                                                    .orElse(comment.getCreateDate()))
                                            .build());
                        }
                    }
                    return ReportsResponse.builder()
                            .title(report.getTitle())
                            .description(report.getDescription())
                            .author(report.getEmployeeId())
                            .reportedDate(report.getCreateDate())
                            .status(report.getStatus())
                            .comments(commentResponseList)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public FacilityReport createFacilityReport(String employee_id, FacilityReportRequest request) {
        // create new facility report
        FacilityReport report = new FacilityReport();
        report.setEmployeeId(employee_id);
        report.setTitle(request.getTitle());
        report.setDescription(request.getDescription());
        report.setCreateDate(new Date(System.currentTimeMillis()));
        report.setStatus(ReportStatus.Open);

        // update report
        return saveOrUpdateFacilityReport(report);
    }

    public FacilityReport addComment(Long report_id, String author_id, CommentRequest request) throws FacilityReportNotFoundException, AccessDeniedException {
        // get current report and its comments
        FacilityReport report = findReportById(report_id);
        List<FacilityReportDetail> comments = report.getComments();

        if (!report.getEmployeeId().equals(author_id))
            throw new AccessDeniedException("You cannot add comment to others' report");

        // add new report detail to current report
        FacilityReportDetail detail = FacilityReportDetail.builder()
                .report(report)
                .employeeId(author_id)
                .comment(request.getComment())
                .createDate(new Date(System.currentTimeMillis()))
                .build();
        comments.add(detail);
        report.setComments(comments);

        // update report
        return saveOrUpdateFacilityReport(report);
    }

    public FacilityReport updateComment(String employee_id, EditCommentRequest request) throws AccessDeniedException {
        FacilityReport report = repository.findFacilityReportByComments_Id(request.getId());

        for (FacilityReportDetail d : report.getComments()) {
            if (d.getId().equals(request.getId())) {
                if (!d.getEmployeeId().equals(employee_id)) {
                    throw new AccessDeniedException("You cannot edit others' comment");
                }
                d.setComment(request.getComment());
                d.setLastModificationDate(new Date(System.currentTimeMillis()));
                break;
            }
        }
        return saveOrUpdateFacilityReport(report);
    }

    public FacilityReport addHRComment(Long report_id, String author_id, CommentRequest request) throws FacilityReportNotFoundException, AccessDeniedException {
        // get current report and its comments
        FacilityReport report = findReportById(report_id);
        List<FacilityReportDetail> comments = report.getComments();

        // add new report detail to current report
        FacilityReportDetail detail = FacilityReportDetail.builder()
                .report(report)
                .employeeId(author_id)
                .comment(request.getComment())
                .createDate(new Date(System.currentTimeMillis()))
                .build();
        comments.add(detail);
        report.setComments(comments);

        // update report
        return saveOrUpdateFacilityReport(report);
    }

    public FacilityReport saveOrUpdateFacilityReport(FacilityReport facilityReport) {
        return repository.save(facilityReport);
    }
}