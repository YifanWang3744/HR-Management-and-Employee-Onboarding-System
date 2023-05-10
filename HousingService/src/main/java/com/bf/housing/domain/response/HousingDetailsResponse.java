package com.bf.housing.domain.response;

import com.bf.housing.domain.common.ResponseStatus;
import com.bf.housing.entity.Roommate;
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
