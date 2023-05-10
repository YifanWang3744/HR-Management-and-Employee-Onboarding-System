package com.bf.housing.domain.response;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.domain.resultWrapper.HouseDetail;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HouseDetailResponse {
    private ResponseStatus status;
    private HouseDetail data;
}
