package com.beaconfire.onboardingservice.domain.common;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseStatus {
    private Boolean success;
    private String message;
}
