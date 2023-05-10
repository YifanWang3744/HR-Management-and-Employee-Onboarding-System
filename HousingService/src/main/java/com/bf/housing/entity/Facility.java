package com.bf.housing.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "house_id")
    @ToString.Exclude
    @JsonIgnore
    private House house;
    private String type;
    private String description;
    private Long quantity;
    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private Set<FacilityReport> reports;
}
