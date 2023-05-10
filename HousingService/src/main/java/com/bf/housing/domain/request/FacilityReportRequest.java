package com.bf.housing.domain.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FacilityReportRequest {
    private String title;
    private String description;
}
