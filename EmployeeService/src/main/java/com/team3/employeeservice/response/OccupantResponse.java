package com.team3.employeeservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OccupantResponse {
    private ResponseStatus status;
    private Integer num;
}
