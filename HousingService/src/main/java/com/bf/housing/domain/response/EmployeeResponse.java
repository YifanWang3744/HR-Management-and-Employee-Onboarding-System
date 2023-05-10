package com.bf.housing.domain.response;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.entity.Employee;
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
