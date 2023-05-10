//package com.bfteam3.ApplicatonMicroService.domain.entity.Authentication;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.Set;
//
////@Entity
//@Table(name = "user")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@ToString
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "username")
//    private String username;
//    @Column(name = "email")
//    private String email;
//    @Column(name = "password")
//    private String password;
//    @Column(name = "create_date")
//    private Date createDate;
//    @Column(name = "last_modification_date")
//    private Date lastModificationDate;
//    @Column(name = "active_flag")
//    private Boolean activeFlag;
//    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
//    private Set<UserRole> roles;
//    @OneToMany(mappedBy = "createdBy", fetch = FetchType.EAGER)
//    @JsonIgnore
//    @ToString.Exclude
//    private Set<RegistrationToken> registrationToken;
//
//}
