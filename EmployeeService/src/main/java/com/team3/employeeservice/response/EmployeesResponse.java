package com.team3.employeeservice.response;

import com.team3.employeeservice.domain.Employee;
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
