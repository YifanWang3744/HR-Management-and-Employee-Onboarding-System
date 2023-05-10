package com.bf.housing.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class VisaStatus {
    private String id;
    private String visaType;
    private Boolean activeFlag;
    private Date startDate;
    private Date endDate;
    private Date lastModificationDate;
}
