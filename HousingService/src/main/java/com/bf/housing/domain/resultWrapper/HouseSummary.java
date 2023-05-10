package com.bf.housing.domain.resultWrapper;

import com.bf.housing.entity.Landlord;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HouseSummary {
    private Long houseId;
    private String houseAddress;
    private Landlord landlord;
    private Integer occupantsNum;
}
