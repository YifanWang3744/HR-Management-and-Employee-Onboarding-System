package com.bfteam3.ApplicationService.domain.response;

import com.bfteam3.ApplicationService.domain.common.Car;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllCarsResponse {
    private ResponseStatus status;
    private List<Car> cars;
}
