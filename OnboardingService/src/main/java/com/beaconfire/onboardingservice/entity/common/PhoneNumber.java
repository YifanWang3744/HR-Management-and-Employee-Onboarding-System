package com.beaconfire.onboardingservice.entity.common;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PhoneNumber {
    private String cell;
    private String work;
}
