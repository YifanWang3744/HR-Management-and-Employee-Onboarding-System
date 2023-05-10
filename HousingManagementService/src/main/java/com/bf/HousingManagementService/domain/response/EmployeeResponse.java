package com.bf.HousingManagementService.domain.response;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.entity.Employee;
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
