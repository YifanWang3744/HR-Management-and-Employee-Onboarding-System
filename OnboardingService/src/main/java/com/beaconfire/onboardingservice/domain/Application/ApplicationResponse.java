package com.beaconfire.onboardingservice.domain.Application;

import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApplicationResponse {
    private ResponseStatus status;
    private ApplicationForm form;
}
