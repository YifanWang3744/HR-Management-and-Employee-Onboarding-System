package com.bfteam3.ApplicationService.domain.response;
import com.bfteam3.ApplicationService.domain.entity.Employee.Employee;
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
