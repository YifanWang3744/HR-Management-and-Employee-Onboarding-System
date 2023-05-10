package com.bf.HousingManagementService.domain.response;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OccupantResponse {
    private ResponseStatus status;
    private Integer num;
}
