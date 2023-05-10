package com.bf.HousingManagementService.domain.response;

import com.bf.HousingManagementService.domain.common.ResponseStatus;
import com.bf.HousingManagementService.entity.Roommate;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HousingDetailsResponse {
    private ResponseStatus status;
    private String address;
    private List<Roommate> roommateList = new ArrayList<>();
}
