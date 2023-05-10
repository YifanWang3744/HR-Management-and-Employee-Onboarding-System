package com.team3.employeeservice.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PersonalDocument {
    @Id
    private String id;
    private String path;
    private String title;
    private String comment;
    @CreatedDate
    private Date createDate;
}
