package com.team3.employeeservice.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VisaStatusRequest {

    @NotNull(message = "visaType cannot be empty")
    private String visaType;

    @NotNull(message = "startDate cannot be empty")
    private String startDate;

    @NotNull(message = "endDate cannot be empty")
    private String endDate;

}
