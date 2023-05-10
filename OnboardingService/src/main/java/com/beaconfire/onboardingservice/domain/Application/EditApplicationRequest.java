package com.beaconfire.onboardingservice.domain.Application;
import com.beaconfire.onboardingservice.entity.Application.Car;
import com.beaconfire.onboardingservice.entity.common.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EditApplicationRequest {
    private Name name;
    private String profilePic;
    private PhoneNumber phoneNumber;
    private Car car;
    private String email;
    private String ssn;
    private String dob;
    private String gender;
    private DriversLicense driversLicense;
    private List<Address> address;
    private List<Contact> referenceAndContact;
    private VisaStatus visaStatus;
    private HashMap<String, String> documents;
}
