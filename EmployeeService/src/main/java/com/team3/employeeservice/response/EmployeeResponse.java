package com.team3.employeeservice.response;

import com.team3.employeeservice.domain.Employee;
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
