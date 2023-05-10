//package com.bfteam3.ApplicatonMicroService.domain.entity.Authentication;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.Set;
//
////@Entity
//@Table(name = "role")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@ToString
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "role_name")
//    private String roleName;
//    @Column(name = "role_desc")
//    private String roleDescription;
//    @Column(name = "create_date")
//    private Date createDate;
//    @Column(name = "last_modification_date")
//    private Date lastModificationDate;
//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//    private Set<UserRole> users;
//}
