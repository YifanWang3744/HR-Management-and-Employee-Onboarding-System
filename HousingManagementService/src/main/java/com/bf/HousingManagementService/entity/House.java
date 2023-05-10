package com.bf.HousingManagementService.entity;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class House {
    private Long id;
    private Landlord landlord;
    private String address;
    private Integer maxOccupant;
    private Boolean full;
    private Set<Facility> facilities;
}
