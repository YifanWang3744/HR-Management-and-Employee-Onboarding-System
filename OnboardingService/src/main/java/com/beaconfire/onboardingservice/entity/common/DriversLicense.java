package com.beaconfire.onboardingservice.entity.common;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DriversLicense {
    private String isOwn;
    private String number;
    private LocalDate expirationDate;
}
