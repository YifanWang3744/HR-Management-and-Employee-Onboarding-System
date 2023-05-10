package com.bf.HousingManagementService.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Facility {
    private Long id;
    @ToString.Exclude
    @JsonIgnore
    private House house;
    private String type;
    private String description;
    private Long quantity;
    @JsonIgnore
    @ToString.Exclude
    private Set<FacilityReport> reports;
}
