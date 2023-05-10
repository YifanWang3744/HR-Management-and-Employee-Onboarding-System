package com.beaconfire.onboardingservice.domain.Application;
import com.beaconfire.onboardingservice.entity.common.Contact;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReferenceAndContact {
    private Contact reference;
    private Contact contact;

}
