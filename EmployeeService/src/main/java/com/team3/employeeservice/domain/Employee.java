package com.team3.employeeservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "Employee")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Employee {
    @Id
    private String id;
    private Long userId;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    private String middleName;
    private String preferredName;
    private String profilePicture = "https://www.nicepng.com/png/detail/933-9332131_profile-picture-default-png.png";
    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Email should follow the format name@domain")
    private String email;
    @NotNull
    private String cellPhone;
    private String alternatePhone;
    @NotBlank(message = "Gender is mandatory")
    private String gender;
    @NotBlank(message = "SSN is mandatory")
    private String ssn;
    @NotNull(message = "Date of birth is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date dateOfBirth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date endDate;
    private String driverLicense;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date driverLicenseExpiration;
    private Long houseId;
    @NotEmpty
    private List<Contact> contactList = new ArrayList<>();
    @NotEmpty
    private List<Address> addressList = new ArrayList<>();
    private List<VisaStatus> visaStatusList = new ArrayList<>();
    private List<PersonalDocument> personalDocumentList = new ArrayList<>();
}
