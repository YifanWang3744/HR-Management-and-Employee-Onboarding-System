package com.bfteam3.ApplicationService.domain.request;
import com.bfteam3.ApplicationService.domain.common.*;
import com.bfteam3.ApplicationService.domain.entity.Employee.VisaStatus;
import lombok.*;

import java.util.Date;
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
    private Date dob;
    private String gender;
    private DriversLicense driversLicense;
    private List<Address> address;
    private List<Contact> referenceAndContact;
    private VisaStatus visaStatus;
    private HashMap<String, String> documents;
}
