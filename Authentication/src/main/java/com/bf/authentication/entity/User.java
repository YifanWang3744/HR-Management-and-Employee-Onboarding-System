package com.bf.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "last_modification_date")
    private Date lastModificationDate;
    @Column(name="active_flag")
    private Boolean activeFlag;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> roles;
    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    @ToString.Exclude
    private Set<RegistrationToken> registrationToken;
}
