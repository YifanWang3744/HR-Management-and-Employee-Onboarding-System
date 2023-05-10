package com.team3.employeeservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Address {
    @Id
    private String id;
    @NotBlank(message = "address line 1 is required")
    private String addressLine1;
    private String addressLine2;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "state is required")
    private String state;
    @NotBlank(message = "zip code is required")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid zip code format")
    private String zipCode;
}
