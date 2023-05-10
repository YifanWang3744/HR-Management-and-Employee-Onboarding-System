package com.bf.HousingManagementService.domain.response;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.entity.Car;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllCarsResponse {
    private ResponseStatus status;
    private List<Car> cars;
}
