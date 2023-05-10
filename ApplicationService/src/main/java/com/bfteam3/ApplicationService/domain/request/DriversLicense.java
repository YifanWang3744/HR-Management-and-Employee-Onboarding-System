package com.bfteam3.ApplicationService.domain.request;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DriversLicense {
    private String isOwn;
    private String number;
    private Date expirationDate;
}
