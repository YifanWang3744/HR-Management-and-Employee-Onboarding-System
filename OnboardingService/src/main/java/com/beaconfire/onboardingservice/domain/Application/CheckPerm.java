package com.beaconfire.onboardingservice.domain.Application;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CheckPerm {
    private String status;
    private String type;
}
