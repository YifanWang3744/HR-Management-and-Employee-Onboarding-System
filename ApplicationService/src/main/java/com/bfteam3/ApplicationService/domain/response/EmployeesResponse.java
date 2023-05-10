package com.bfteam3.ApplicationService.domain.response;
import com.bfteam3.ApplicationService.domain.entity.Employee.Employee;
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