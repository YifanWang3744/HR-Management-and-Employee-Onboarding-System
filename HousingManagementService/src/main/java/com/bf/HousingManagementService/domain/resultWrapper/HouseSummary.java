package com.bf.HousingManagementService.domain.resultWrapper;

import com.bf.HousingManagementService.entity.Landlord;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HouseSummary {
    private Long houseId;
    private String houseAddress;
    private Landlord landlord;
    private Integer occupantsNum;
}
