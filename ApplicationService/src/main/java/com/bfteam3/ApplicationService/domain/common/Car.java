package com.bfteam3.ApplicationService.domain.common;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "make")
    private String make;
    @Column(name = "employee_id")
    private String employeeId;
    @Column(name = "model")
    private String model;
    @Column(name = "color")
    private String color;
}
