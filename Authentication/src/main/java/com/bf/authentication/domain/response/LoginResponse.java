package com.bf.authentication.domain.response;

import com.bf.authentication.domain.common.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private ResponseStatus status;
    private String token;
}
