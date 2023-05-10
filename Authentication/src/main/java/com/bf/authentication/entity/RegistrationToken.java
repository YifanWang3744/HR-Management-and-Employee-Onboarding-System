package com.bf.authentication.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registration_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegistrationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String email;
    @Column(name = "expiration_date")
    private Date expirationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
