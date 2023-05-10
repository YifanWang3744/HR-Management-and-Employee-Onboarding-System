package com.beaconfire.onboardingservice.domain.Application;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WorkAuth {
    private String type;
    private String other;
    private String startDate;
    private String endDate;
}
