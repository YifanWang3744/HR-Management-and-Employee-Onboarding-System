package com.team3.employeeservice.response;

import com.team3.employeeservice.domain.VisaStatus;
import lombok.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VisaStatusListResponse {
    private ResponseStatus status;
    private List<VisaStatus> visaStatusList;
}
