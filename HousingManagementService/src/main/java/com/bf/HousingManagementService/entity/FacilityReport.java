package com.bf.HousingManagementService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FacilityReport {

    private Long id;
    private String employeeId;
    @JsonIgnore
    @ToString.Exclude
    private Facility facility;
    private String title;
    private String description;
    private Date createDate;
    private ReportStatus status;
    private FacilityReportDetail facilityReportDetail;
}
