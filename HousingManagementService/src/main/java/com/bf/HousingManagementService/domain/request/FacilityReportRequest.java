package com.bf.HousingManagementService.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FacilityReportRequest {
    private String title;
    private String description;
}
