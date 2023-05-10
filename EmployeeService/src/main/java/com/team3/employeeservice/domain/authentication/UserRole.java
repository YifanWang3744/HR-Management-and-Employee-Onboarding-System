package com.team3.employeeservice.domain.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
