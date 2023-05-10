package com.team3.employeeservice.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoteEmployeeRequest {
    private Long userId;
    private String email;
}
