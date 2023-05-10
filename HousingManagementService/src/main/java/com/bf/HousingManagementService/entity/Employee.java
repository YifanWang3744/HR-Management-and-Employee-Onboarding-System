package com.bf.HousingManagementService.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Employee {
    private String id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String preferredName;
    private String profilePicture = "https://www.nicepng.com/png/detail/933-9332131_profile-picture-default-png.png";
    private String email;
    private String cellPhone;
    private String alternatePhone;
    private String gender;
    private String ssn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate endDate;
    private String driverLicense;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate driverLicenseExpiration;
    private Long houseId;
    private List<Contact> contactList = new ArrayList<>();
    private List<Address> addressList = new ArrayList<>();
    private List<VisaStatus> visaStatusList = new ArrayList<>();
    private List<PersonalDocument> personalDocumentList = new ArrayList<>();
}
