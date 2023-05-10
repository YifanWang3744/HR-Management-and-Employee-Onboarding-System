package com.bfteam3.ApplicationService.domain.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class HouseAssignmentResponse {
    private ResponseStatus status;
    private Long houseId;
}
