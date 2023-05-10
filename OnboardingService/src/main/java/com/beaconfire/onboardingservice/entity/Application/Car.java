package com.beaconfire.onboardingservice.entity.Application;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Car {
    @Id
    private Long id;
    private String make;
    private String employeeId;
    private String model;
    private String color;
}
