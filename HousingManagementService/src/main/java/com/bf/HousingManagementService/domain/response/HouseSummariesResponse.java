package com.bf.HousingManagementService.domain.response;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.domain.resultWrapper.HouseSummary;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HouseSummariesResponse {
    private ResponseStatus status;
    private List<HouseSummary> data;
}
