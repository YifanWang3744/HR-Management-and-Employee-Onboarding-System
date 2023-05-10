package com.team3.employeeservice.resultWrapper;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisaWrapper {
    private String visaType;
    private Date endDate;
    private Long daysLeft;
}
