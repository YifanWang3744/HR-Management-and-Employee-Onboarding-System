package com.bf.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude
    private User user;
    @ManyToOne
    @JoinColumn(name = "role_id")
//    @JsonIgnore
//    @ToString.Exclude
    private Role role;
    @Column(name="active_flag")
    private Boolean activeFlag;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "last_modification_date")
    private Date lastModificationDate;
}
