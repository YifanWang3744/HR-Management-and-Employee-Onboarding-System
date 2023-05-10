package com.beaconfire.onboardingservice.domain.Application;

import com.beaconfire.onboardingservice.entity.Application.Car;
import com.beaconfire.onboardingservice.entity.common.Address;
import com.beaconfire.onboardingservice.entity.common.Name;
import com.beaconfire.onboardingservice.entity.common.PhoneNumber;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralInfo {
    private Name name;
    private Address address;
    private PhoneNumber phoneNumber;
    private Car car;
    private String ssn;
    private String dob;
    private String gender;
    private CheckPerm perm;
}
