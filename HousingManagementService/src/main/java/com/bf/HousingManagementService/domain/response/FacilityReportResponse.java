package com.bf.HousingManagementService.domain.response;

import com.bf.HousingManagementService.entity.ReportStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class FacilityReportResponse {
    private String title;
    private String description;
    private String author;
    private Date reportedDate;
    private ReportStatus status;
    private CommentResponse comment;
}
