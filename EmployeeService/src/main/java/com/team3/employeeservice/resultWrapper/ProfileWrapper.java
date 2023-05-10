package com.team3.employeeservice.resultWrapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileWrapper {
    private String id;
    private String firstName;
    private String lastName;
    private String SSN;
    private String title;
    private String phone;
    private String email;
    private String profilePicture;
}
