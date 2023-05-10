package com.team3.employeeservice.domain.authentication;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Role {
    private Long id;
    private String roleName;
    private String roleDescription;
    private Date createDate;
    private Date lastModificationDate;
    private Set<UserRole> users;
}
