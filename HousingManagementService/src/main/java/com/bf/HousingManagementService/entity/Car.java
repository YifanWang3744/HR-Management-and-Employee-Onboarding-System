package com.bf.HousingManagementService.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Car {
    private Long id;
    private String make;
    private String employeeId;
    private String model;
    private String color;
}