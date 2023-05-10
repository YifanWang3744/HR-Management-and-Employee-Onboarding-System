package com.beaconfire.onboardingservice.domain.Application;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.Application.Car;
import com.beaconfire.onboardingservice.entity.common.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApplicationFormResponse {
    private ResponseStatus status;
    private Name name;
    private String profilePic;
    private PhoneNumber phoneNumber;
    private List<Car> car;
    private String email;
    private String ssn;
    private LocalDate dob;
    private String gender;
    private DriversLicense driversLicense;
    private List<Address> address;
    private List<Contact> referenceAndContact;
    private VisaStatus visaStatus;
    private List<PersonalDocument> documents;
}
