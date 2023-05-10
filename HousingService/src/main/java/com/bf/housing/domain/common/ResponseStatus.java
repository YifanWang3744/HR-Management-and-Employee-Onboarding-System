package com.bf.housing.domain.common;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ResponseStatus {
    private Boolean success;
    private String message;
}
