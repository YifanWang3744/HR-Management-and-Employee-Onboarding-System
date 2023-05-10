package com.team3.employeeservice.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class House {
    private Long id;
    private Long landloadId;
    private String address;
    private Integer maxOccupant;
}
