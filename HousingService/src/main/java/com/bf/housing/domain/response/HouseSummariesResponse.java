package com.bf.housing.domain.response;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.domain.resultWrapper.HouseSummary;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HouseSummariesResponse {
    private ResponseStatus status;
    private List<HouseSummary> data;
}
