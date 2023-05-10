package com.beaconfire.onboardingservice.entity.Authentication;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRole {
    private Long id;
    private User user;
    private Role role;
    private Boolean activeFlag;
    private Date createDate;
    private Date lastModificationDate;
}
