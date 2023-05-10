package com.bf.housing.domain.response;

import com.bf.housing.entity.ReportStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class ReportsResponse {
    private String title;
    private String description;
    private String author;
    private Date reportedDate;
    private ReportStatus status;
    private List<CommentResponse> comments;
}
