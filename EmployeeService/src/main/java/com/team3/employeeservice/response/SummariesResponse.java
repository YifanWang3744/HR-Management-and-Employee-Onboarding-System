package com.team3.employeeservice.response;

import com.team3.employeeservice.resultWrapper.ProfileWrapper;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SummariesResponse {
    private ResponseStatus status;
    private List<ProfileWrapper> summaryList;
}
