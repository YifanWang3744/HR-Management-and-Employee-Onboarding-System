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
public class Landlord {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String cellphone;
    @ToString.Exclude
    @JsonIgnore
    private Set<House> houses;
}
