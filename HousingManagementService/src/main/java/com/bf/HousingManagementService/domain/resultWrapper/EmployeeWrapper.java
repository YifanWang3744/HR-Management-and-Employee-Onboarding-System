package com.bf.HousingManagementService.domain.resultWrapper;

import com.bf.HousingManagementService.entity.Car;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeWrapper {
    private String id;
    private String profilePicture;
    private String name;
    private String phone;
    private Car car;
}
