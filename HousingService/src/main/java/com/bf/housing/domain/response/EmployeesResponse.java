package com.bf.housing.domain.response;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.entity.Employee;
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
