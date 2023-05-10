package com.team3.employeeservice.resultWrapper;

import com.team3.employeeservice.domain.VisaStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusWrapper {
    private String name;
    private List<VisaWrapper> visaWrapperList;

}
