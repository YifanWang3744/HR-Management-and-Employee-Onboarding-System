package com.beaconfire.onboardingservice.entity.Application;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApplicationWorkFlow {
    @Id
    private Long id;
    private String employeeId;
    private Date createDate;
    private Date lastModificationDate;
    private ApplicationStatus applicationStatus;
    private String comment;
}
