package com.team3.employeeservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class VisaStatus {
    @Id
    private String id;
    private String visaType;
    private Boolean activeFlag;
    private Date startDate;
    private Date endDate;
    private Date lastModificationDate;
}
