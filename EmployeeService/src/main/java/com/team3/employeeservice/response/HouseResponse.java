package com.team3.employeeservice.response;

import com.team3.employeeservice.domain.House;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HouseResponse {
    private ResponseStatus status;
    private House house;
}
