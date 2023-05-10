package com.bf.authentication.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoteTokenRequest {
    private String email;
    private String authorization;
}
