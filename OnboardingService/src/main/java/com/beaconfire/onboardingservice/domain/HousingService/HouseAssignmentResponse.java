package com.beaconfire.onboardingservice.domain.HousingService;

import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class HouseAssignmentResponse {
    private ResponseStatus status;
    private Long houseId;
}
