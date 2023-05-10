package com.bf.HousingManagementService.domain.response;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.entity.House;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HouseListResponse {
    private ResponseStatus responseStatus;
    private List<House> houseList;
}
