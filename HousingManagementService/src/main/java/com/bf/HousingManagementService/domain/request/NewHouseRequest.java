package com.bf.HousingManagementService.domain.request;

import com.bf.HousingManagementService.entity.Facility;
import lombok.Data;

import java.util.Set;

@Data
public class NewHouseRequest {
    private String address;
    private Integer maxOccupants;
    private String firstName;
    private String lastName;
    private String email;
    private String cellphone;
    private Set<Facility> facilities;
}
