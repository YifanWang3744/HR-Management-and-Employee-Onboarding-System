package com.beaconfire.onboardingservice.domain.HousingService;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.HousingService.House;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HouseResponse {
    private ResponseStatus status;
    private House house;
}
