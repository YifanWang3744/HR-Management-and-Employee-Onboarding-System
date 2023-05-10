package com.bf.housing.domain.response;

import com.bf.housing.domain.common.ResponseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OccupantResponse {
    private ResponseStatus status;
    private Integer num;
}
