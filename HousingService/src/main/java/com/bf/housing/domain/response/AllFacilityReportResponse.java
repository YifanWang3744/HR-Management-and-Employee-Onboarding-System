package com.bf.housing.domain.response;

import com.bf.housing.domain.common.ResponseStatus;
import lombok.*;

import java.util.List;

@Builder
@ToString
@Getter
@Setter
public class AllFacilityReportResponse {
    private ResponseStatus status;
    private List<ReportsResponse> data;
}
