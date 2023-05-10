package com.team3.employeeservice.request;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactInfoRequest {
    @NotBlank(message = "Cell phone is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number format")
    private String cellPhone;
    @Pattern(regexp = "^\\d{10}$|^$", message = "Invalid phone number format")
    private String workPhone;
}
