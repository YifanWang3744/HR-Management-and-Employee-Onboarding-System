package com.bfteam3.ApplicationService.domain.entity.Employee;
import com.bfteam3.ApplicationService.domain.common.Address;
import com.bfteam3.ApplicationService.domain.common.Contact;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String middleName;
    private String preferredName;
    private String profilePicture = "https://www.nicepng.com/png/detail/933-9332131_profile-picture-default-png.png";
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Pattern(regexp = "\\w+@\\w+")
    private String email;
    @NotNull
    private String cellPhone;
    private String alternatePhone;
    private String gender;
    @NotNull
    private String SSN;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date dateOfBirth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date endDate;
    private String driverLicense;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date driverLicenseExpiration;
    private String houseId;
    @NotEmpty
    private List<Contact> contactList = new ArrayList<>();
    @NotEmpty
    private List<Address> addressList = new ArrayList<>();
    private List<VisaStatus> visaStatusList = new ArrayList<>();
    private List<PersonalDocument> personalDocumentList = new ArrayList<>();
}