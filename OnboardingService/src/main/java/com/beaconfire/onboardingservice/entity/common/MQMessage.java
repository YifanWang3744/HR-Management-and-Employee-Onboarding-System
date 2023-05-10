package com.beaconfire.onboardingservice.entity.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MQMessage {
    private String email;
    private String invitationLink;
}
