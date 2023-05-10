package com.team3.employeeservice.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Contact {
    @Id
    private String id;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    private String middleName;
    @NotBlank(message = "Cell phone is mandatory")
    private String cellPhone;
    private String alternatePhone;
    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Email should follow the format name@domain")
    private String email;
    @NotBlank(message = "Relationship is mandatory")
    private String relationship;
    private String type;
}
