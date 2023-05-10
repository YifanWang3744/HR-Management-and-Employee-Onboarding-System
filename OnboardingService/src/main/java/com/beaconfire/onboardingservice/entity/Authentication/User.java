package com.beaconfire.onboardingservice.entity.Authentication;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Date createDate;
    private Date lastModificationDate;
    private Boolean activeFlag;
    private Set<UserRole> roles;
}
