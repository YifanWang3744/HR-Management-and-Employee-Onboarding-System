package com.bfteam3.ApplicationService.domain.request;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DriversLicenseRequest {
    private String isOwn;
    private String number;
    private String expirationDate;
}
