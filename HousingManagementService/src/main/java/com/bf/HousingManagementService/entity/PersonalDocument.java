package com.bf.HousingManagementService.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PersonalDocument {
    private String id;
    private String path;
    private String title;
    private String comment;
    private Date createDate;
}
