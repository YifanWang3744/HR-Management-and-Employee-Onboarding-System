package com.beaconfire.onboardingservice.domain.Application;

import com.beaconfire.onboardingservice.entity.Application.Car;
import com.beaconfire.onboardingservice.entity.common.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ApplicationForm {
    private Name name;
    private String profilePic;
    private PhoneNumber phoneNumber;
    private List<Car> car;
    private String email;
    private String SSN;
    private Date DOB;
    private String gender;
    private DriversLicense driversLicense;
    private List<Address> address;
    private List<Contact> referenceAndContact;
    private List<VisaStatus> visaStatus;
    private List<PersonalDocument> documents;
}
