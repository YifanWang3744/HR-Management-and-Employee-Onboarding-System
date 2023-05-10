package com.beaconfire.onboardingservice.entity.common;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisaStatus {
    @Id
    private String id;
    private String visaType;
    private Boolean activeFlag;
    private Date startDate;
    private Date endDate;
    private Date lastModificationDate;
}
