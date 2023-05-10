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
public class FacilityReportDetail {
    private Long id;
    @ToString.Exclude
    @JsonIgnore
    private FacilityReport report;
    private String employeeId;
    private String comment;
    private Date createDate;
    private Date lastModificationDate;
}
