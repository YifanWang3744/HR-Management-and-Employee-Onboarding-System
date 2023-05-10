package com.bf.authentication.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoteEmployeeRequest {
    private Long userId;
    private String email;
}
