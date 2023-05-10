package com.bf.housing.domain.response;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.entity.FacilityReportDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllReportDetailResponse {
    private ResponseStatus status;
    private List<FacilityReportDetail> comments;
}
