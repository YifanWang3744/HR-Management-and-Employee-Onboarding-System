package com.beaconfire.onboardingservice.domain.Application;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DriversLicenseRequest {
    private String isOwn;
    private String number;
    private String expirationDate;
}
