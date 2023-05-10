package com.bf.housing.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;
    private String address;
    @Column(name = "max_occupant")
    private Integer maxOccupant;
    private Boolean full;
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private Set<Facility> facilities;
}
