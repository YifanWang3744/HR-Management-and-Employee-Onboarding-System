package com.bf.HousingManagementService.domain.response;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.domain.resultWrapper.HouseDetail;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HouseDetailResponse {
    private ResponseStatus status;
    private HouseDetail data;
}
