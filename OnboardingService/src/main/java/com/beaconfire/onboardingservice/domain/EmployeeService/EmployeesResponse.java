package com.beaconfire.onboardingservice.domain.EmployeeService;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.EmployeeService.Employee;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeesResponse {
    private ResponseStatus status;
    private List<Employee> data;
}