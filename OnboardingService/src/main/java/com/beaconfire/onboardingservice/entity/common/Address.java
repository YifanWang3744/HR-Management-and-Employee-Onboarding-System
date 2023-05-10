package com.beaconfire.onboardingservice.entity.common;
import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Id
    private String id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private Integer zipCode;
}
