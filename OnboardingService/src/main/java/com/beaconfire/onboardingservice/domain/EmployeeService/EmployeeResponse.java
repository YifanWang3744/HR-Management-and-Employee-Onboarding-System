package com.beaconfire.onboardingservice.domain.EmployeeService;
import com.beaconfire.onboardingservice.domain.common.ResponseStatus;
import com.beaconfire.onboardingservice.entity.EmployeeService.Employee;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeResponse {
    private ResponseStatus status;
    private Employee data;
}
