package com.bf.housing.domain.request;

import com.bf.housing.entity.Facility;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class NewHouseRequest {
    private String address;
    private Integer maxOccupants;
    private String firstName;
    private String lastName;
    private String email;
    private String cellphone;
    private Set<Facility> facilities;
}
