package com.team3.employeeservice.response;

import com.team3.employeeservice.domain.VisaStatus;
import com.team3.employeeservice.resultWrapper.StatusWrapper;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StatusesResponse {
    private ResponseStatus status;
    private List<StatusWrapper> visaStatusList;
}
